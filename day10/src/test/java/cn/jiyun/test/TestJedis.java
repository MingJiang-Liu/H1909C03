package cn.jiyun.test;

import java.util.HashMap;

import org.junit.Test;

import redis.clients.jedis.Jedis;

public class TestJedis {

	@Test
	public void testJedis(){
		//实例化jedis 地址+端口号
		Jedis jedis = new Jedis("127.0.0.1",6379);
		jedis.set("name", "迪丽热巴");
		System.out.println(jedis.get("name"));
		
	}
	
	@Test
	public void TestJedisPool(){
		//实例化工具类
		JedisUtil jedisUtil = new JedisUtil();
		//获取链接
		Jedis jedis = jedisUtil.getJedis();
		//执行
		
		HashMap<String,String> map = new HashMap<String, String>();
		
		map.put("name", "迪丽热巴");
		
		jedis.hmset("student", map);
		
		System.out.println(jedis.hgetAll("student"));
		
	}
}
