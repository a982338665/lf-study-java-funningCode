package pers.li.funning.entity;

import lombok.Data;

@Data
public class SOrderSeckill {
	private Long id;
	private Long userId;
	private Long  orderId;
	private Long goodsId;

}
