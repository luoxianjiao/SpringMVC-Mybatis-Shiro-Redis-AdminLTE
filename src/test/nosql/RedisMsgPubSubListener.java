package test.nosql;

import redis.clients.jedis.JedisPubSub;

/**
 * 监听器
 * @author xianjiao.luo
 *
 */
public class RedisMsgPubSubListener extends JedisPubSub{

	@Override
	public void onMessage(String channel, String message) {
		  System.out.println("channel:" + channel + "， receives message :" + message);  
        this.unsubscribe();//取消订阅 
	}

	/**
	 * SUBSCRIBE
	 * 1、订阅给指定频道的信息。
	 * 2、一旦客户端进入订阅状态，客户端就只可接受订阅相关的命令SUBSCRIBE、PSUBSCRIBE、UNSUBSCRIBE和PUNSUBSCRIBE除了这些命令，其他命令一律失效。 
	 */
	@Override
	public void onSubscribe(String channel, int subscribedChannels) {
		System.out.println("channel:" + channel + "， is been subscribed:" + subscribedChannels);
	}

	/**
	 * UNSUBSCRIBE: 取消订阅指定的频道，如果不指定，则取消订阅所有的频道。
	 */
	@Override
	public void onUnsubscribe(String channel, int subscribedChannels) {
		System.out.println("channel:" + channel + "， is been unsubscribed:" + subscribedChannels);
	}

	@Override
	public void onPMessage(String s, String s1, String s2) {
		// TODO Auto-generated method stub
		super.onPMessage(s, s1, s2);
	}
	
//PUNSUBSCRIBE:可以退订指定的规则，如果没有参数会退订所有的规则。
	/**PUNSUBSCRIBE应该注意一下两点：
	 * 1、使用PUNSUBSCRIBE命令只能退订通过PSUBSCRIBE命令订阅的规则，不会影响SUBSCRIBE订阅的频道。
	   2、使用PUNSUBSCRIBE命令退订某个规则时不会将其中通配符展开，
		   而是严格的进行==字符串匹配==，所以PUNSUBSCRIBE *无法退订PUNSUBSCRIBE channal1.*规则，
		   而必须使用PUNSUBSCRIBE channal1.*才能退订
	 */
	@Override
	public void onPUnsubscribe(String s, int i) {
		// TODO Auto-generated method stub
		super.onPUnsubscribe(s, i);
	}
//PSUBSCRIBE:订阅给定的模式(patterns)。	
	@Override
	public void onPSubscribe(String s, int i) {
		// TODO Auto-generated method stub
		super.onPSubscribe(s, i);
	}

	
	
	
}
