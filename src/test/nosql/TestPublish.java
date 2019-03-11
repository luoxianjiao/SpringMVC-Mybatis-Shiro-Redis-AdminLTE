package test.nosql;

import cn.itcast.ssm.utils.RedisClient;
import redis.clients.jedis.Jedis;


/**
 * 发布者
 * @author xianjiao.luo
 *
 */
public class TestPublish {

	public static void main(String[] args) throws Exception {
		Jedis jedis = RedisClient.getJedis();
		//PUBLISH:将信息 message 发送到指定的频道 channel。返回收到消息的客户端数量。		
		System.out.println(jedis.publish("redisChat", "Redis is a great caching technique"));  
        Thread.sleep(2000);  
        System.out.println(jedis.publish("redisChat", "build your dream"));  
        Thread.sleep(2000);  
        System.out.println(jedis.publish("redisChat", "over"));
        Thread.sleep(2000);  
        System.out.println(jedis.publish("redisChat1", "over")); 
	}
}
