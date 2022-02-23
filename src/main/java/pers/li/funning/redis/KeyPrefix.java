package pers.li.funning.redis;

/**
 * redis的key值基础接口
 */
public interface KeyPrefix {

	/**
	 * 过期时间
	 * @return
	 */
	int getExpireSeconds();

	/**
	 * 存值的前缀
	 * @return
	 */
	String getPrefix();

}
