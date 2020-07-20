package cn.jiyun.test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisUtil {

	public Jedis getJedis(){
		
		//实例化连接池配置
		JedisPoolConfig config = new JedisPoolConfig();
		//设定最大连接数
		config.setMaxTotal(20);
		//设定最大空闲书
		config.setMaxIdle(5);
		//实例化连接池
		JedisPool jedisPool =new JedisPool(config,"127.0.0.1",6379);
		//获取链接
		Jedis jedis = jedisPool.getResource();
		return jedis;
		
	}
}
