package test.nosql;

import cn.itcast.ssm.utils.RedisClient;
import redis.clients.jedis.Jedis;


/**
 * 订阅者
 * 发布订阅(pub/sub)是一种消息通信模式，主要的目的是解耦消息发布者和消息订阅者之间的耦合，
 * 这点和设计模式中的观察者模式比较相似。pub /sub不仅仅解决发布者和订阅者直接代码级别耦合也解决两者在物理部署上的耦合。
 * redis作为一个pub/sub server，在订阅者和发布者之间起到了消息路由的功能。
 * 订阅者可以通过subscribe和psubscribe命令向redis server订阅自己感兴趣的消息类型，redis将消息类型称为通道(channel)。
 * 当发布者通过publish命令向redis server发送特定类型的消息时。
 * 订阅该消息类型的全部client都会收到此消息。这里消息的传递是多对多的。
 * 一个client可以订阅多个 channel,也可以向多个channel发送消息。
 * @author xianjiao.luo
 *
 */
public class TestSubscribe {

	public static void main(String[] args) {
		Jedis jedis = RedisClient.getJedis();
		RedisMsgPubSubListener listener = new RedisMsgPubSubListener();
		/** 
         * 注意：subscribe是一个阻塞的方法，在取消订阅该频道前，会一直阻塞在这，只有当取消了订阅才会执行下面的other code， 
         * 参考上面代码，我在onMessage里面收到消息后，调用了this.unsubscribe(); 来取消订阅，这样才会执行后面的other code
         * 
         * 
		  SUBSCRIBE
		  1、订阅给指定频道的信息。
		  2、一旦客户端进入订阅状态，客户端就只可接受订阅相关的命令SUBSCRIBE、PSUBSCRIBE、UNSUBSCRIBE和PUNSUBSCRIBE除了这些命令，其他命令一律失效。 	 
         */  
        jedis.subscribe(listener, "redisChat");//channel:通道
        //如果没有取消订阅,方法将一直堵塞在此处不会向下执行
        
        //to do others 
        System.out.println("TestSubscribe ending !!!");
        
	}
}
