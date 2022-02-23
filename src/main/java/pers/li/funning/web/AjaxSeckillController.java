package pers.li.funning.web;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.li.funning.entity.SOrderInfo;
import pers.li.funning.entity.SOrderSeckill;
import pers.li.funning.entity.SUser;
import pers.li.funning.rabbitmq.MQSender;
import pers.li.funning.rabbitmq.SeckillMessage;
import pers.li.funning.redis.RedisService;
import pers.li.funning.redis.service.GoodsKey;
import pers.li.funning.result.CodeMsg;
import pers.li.funning.result.Result;
import pers.li.funning.service.SGoodService;
import pers.li.funning.service.SOrderService;
import pers.li.funning.service.SeckillService;
import pers.li.funning.vo.SGoodsVo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 秒杀静态化：数据传输使用json，亲后端交互使用ajax
 */
@Controller
@RequestMapping("/seckill")
public class AjaxSeckillController implements InitializingBean {

    @Autowired
    SGoodService sGoodService;
    @Autowired
    SOrderService sOrderService;
    @Autowired
    SeckillService seckillService;
    @Autowired
    RedisService redisService;
    @Autowired
    MQSender mqSender;

    private Map<Long,Boolean> map = new HashMap<>();
    /**
     * QPS:1306
     * 5000 * 10
     * */
    /**
     * GET POST有什么区别？
     */
    @RequestMapping(value = "/seckill", method = RequestMethod.POST)
    @ResponseBody
    public Result<SOrderInfo> miaosha(Model model, SUser user,
                                      @RequestParam("goodsId") long goodsId) {
        model.addAttribute("user", user);
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        //判断库存//10个商品，req1 req2
        SGoodsVo goods = sGoodService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getGoodsStock();
        if (stock <= 0) {
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }
        //判断是否已经秒杀到了
        SOrderSeckill orderSeckill = sOrderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if (orderSeckill != null) {
            return Result.error(CodeMsg.REPEATE_MIAOSHA);
        }
        //减库存 下订单 写入秒杀订单
        SOrderInfo orderInfo = seckillService.seckill(user, goods);
        return Result.success(orderInfo);
    }
    /**
     * QPS:1306
     * 5000 * 10
     * */
    /**
     * GET POST有什么区别？
     */
    @RequestMapping(value = "/seckill-mq", method = RequestMethod.POST)
    @ResponseBody
    public Result<Integer> miaoshaMQ(Model model, SUser user,
                                      @RequestParam("goodsId") long goodsId) {
        model.addAttribute("user", user);
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        //内存标记
        Boolean aBoolean = map.get(goodsId);
        if(aBoolean){
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }
        //redis预减库存：不能准确卖出指定商品个数，会少不会超
        Long stock = redisService.decr(GoodsKey.getSeckillGoodsStock, "" + goodsId);
        if(stock<0){
            map.put(goodsId,true);
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }
        //判断是否已经秒杀到了
        SOrderSeckill orderSeckill = sOrderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if (orderSeckill != null) {
            return Result.error(CodeMsg.REPEATE_MIAOSHA);
        }
        //入队MQ
        SeckillMessage seckillMessage = new SeckillMessage();
        seckillMessage.setSUser(user);
        seckillMessage.setGoodsId(goodsId);
        mqSender.sendSeckill(seckillMessage);
        //返回排队中
        return Result.success(0);
    }

    /**
     * 容器启动后的回调方法 --> 即系统初始化数据
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<SGoodsVo> sGoodsVos = sGoodService.listSGoodsVo();
        if (sGoodsVos == null) {
            return;
        }
        for (SGoodsVo sGoodsVo : sGoodsVos
                ) {
//            存储秒杀商品库存
            redisService.set(GoodsKey.getSeckillGoodsStock,""+ sGoodsVo.getId(), sGoodsVo.getSeckillStock());
            map.put(sGoodsVo.getId(),false);
        }
    }
}
