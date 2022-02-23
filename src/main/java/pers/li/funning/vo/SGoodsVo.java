package pers.li.funning.vo;

import lombok.Data;
import pers.li.funning.entity.SGoods;

import java.util.Date;

/**
 * @author:luofeng
 * @createTime : 2018/10/11 17:42
 */
@Data
public class SGoodsVo extends SGoods {
    private Double seckillPrice;
    private Integer seckillStock;
    private Date startTime;
    private Date endTime;
}
