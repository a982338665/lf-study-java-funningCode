package pers.li.funning.rabbitmq;

import lombok.Data;
import pers.li.funning.entity.SUser;

/**
 * create by lishengbo 2019/5/27
 */
@Data
public class SeckillMessage {
    private SUser sUser;
    private long goodsId;
}
