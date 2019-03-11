package test.nosql;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import cn.itcast.ssm.po.Items;
import cn.itcast.ssm.utils.ObjectUtil;
import cn.itcast.ssm.utils.RedisClient;

/**
 * Redis并没有支持直接的队列结构，可以通过List模拟queue的实现
 * @author xianjiao.luo
 *
 */
public class TestRedisQueue {
	
	private static byte[] redisKey = "key10".getBytes();
	static {
//		push();
	}
	public static void main1(String[] args) {
//		for (int i = 0; i < 100; i++) {
//			pop();
//		}
//		pop();
//		RedisClient.rpoplpush(redisKey, "key11".getBytes());
		
		Items items =(Items) ObjectUtil.bytesToObj(RedisClient.rpop("key11".getBytes()));
		System.out.println(items.getName());
	}
	
	public static void push() {
		for (int i = 0; i < 100; i++) {
			Items items = new Items();
			items.setId(i);
			items.setName("我是第" + i + "个商品");
			RedisClient.lpush(redisKey, ObjectUtil.objToBytes(items));
		}
		
	}
	
	public static void pop() {
		Items items =(Items) ObjectUtil.bytesToObj(RedisClient.rpop(redisKey));
		System.out.println(items.getName());
	}
	
	public static void pop1() {
//		Items items =(Items) ObjectUtil.bytesToObj(RedisClient.lpopList(redisKey));
		@SuppressWarnings("unchecked")
		List<byte[]> list =  RedisClient.lpopList(redisKey);		
		for (int i = 0; i < list.size(); i++) {
			byte[] b = list.get(i);
			Items items = (Items) ObjectUtil.bytesToObj(b);
			System.out.println(items.getName());
		}
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		System.out.println((Items) ObjectUtil.bytesToObj(RedisClient.rpop("redisChat".getBytes())) );
		
	}
}
