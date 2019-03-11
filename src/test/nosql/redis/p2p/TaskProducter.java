package test.nosql.redis.p2p;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import cn.itcast.ssm.po.Items;
import cn.itcast.ssm.utils.ObjectUtil;
import cn.itcast.ssm.utils.RedisClient;
import redis.clients.jedis.Jedis;

public class TaskProducter {

	private int size;
	private int interval;
	private static Jedis jedis;
	private Dispatcher dispatcher;
	private String queueName;
	public TaskProducter(TaskConsumer taskConsumer, String queueName,Jedis jedis) {		
		this.size = 100;//默认一次取100条
		this.interval = 2000;//默认2秒跑一次
		this.dispatcher = new Dispatcher(taskConsumer,queueName, this.size, this.interval, jedis) ;
		this.jedis = jedis;
		this.queueName = queueName;
		this.dispatcher.start();
	}

	public TaskProducter(TaskConsumer taskConsumer,String queueName, int size, int interval, Jedis jedis) {		
		this.size = size;
		this.interval = interval;
//		this.jedis = jedis;
		this.jedis = RedisClient.getJedis();
		this.dispatcher = new Dispatcher(taskConsumer, queueName, size, interval, jedis);
		this.queueName = queueName;
		this.dispatcher.start();
	}

	public void push(Object data) {
		this.jedis.lpush(this.queueName.getBytes(), ObjectUtil.objToBytes(data));			
	}
	/**
	 * 扫描（轮询）调度器
	 * @author xianjiao.luo
	 *
	 */
	private static class Dispatcher implements Runnable {
		private final int size;
		private final int interval;
//		private final Jedis jedis;
		private final TaskConsumer taskConsumer;
		private final String queueName;
		private transient Thread scanThread;
		private final List<Object> bufferList = new LinkedList<>();
		@Override
		public void run() {			
			System.out.println("hello");
			while (true) {
				sleep(this.interval);
				fillDataToBufferList();
				if(bufferList.isEmpty()) continue;
				try {
					this.taskConsumer.handle(bufferList);
				} catch (Exception e) {					
					e.printStackTrace();
				} finally {
					bufferList.clear();
				}
			}
		}		
		public void start() {
			this.scanThread = new Thread(this);
			this.scanThread.start();
		}
		
		public void sleep(long millis) {
			if(this.scanThread ==null) {
				return;
			}
			if(millis < 0) {
				return;
			}
			try {
				Thread.sleep(millis);
			} catch (InterruptedException e) {				
				e.printStackTrace();
			}
			
		}
		public Dispatcher(TaskConsumer taskConsumer,String queueName, int size, int interval, Jedis jedis) {
			super();
			this.size = size;
			this.interval = interval;
//			this.jedis = jedis;
			this.taskConsumer = taskConsumer;
			this.queueName = queueName;
		}
		
		public void fillDataToBufferList() {
			for (int i = 0; i < this.size; i++) {
				byte[] bytes = jedis.rpop(queueName.getBytes());
				if(bytes == null) continue;
				Object object = ObjectUtil.bytesToObj(bytes);
				if(object != null) {
			        this.bufferList.add(object);
				}				
			}
		}
		
		
	}
	
	public static void main(String[] args) throws InterruptedException {
		final TaskProducter logTaskProducter = new TaskProducter(new LogTaskConsumerImpl(), "LOG", 5, 1000, null);
//		final TaskProducter orderTaskProducter = new TaskProducter(new OrderTaskConsumerImpl(), "ORDER", 5, 1000, RedisClient.getJedis());
		for (int i = 0; i < 50; i++) {
			new Thread() {
				public void run() {
					Items item = new Items();
					item.setName("LOG" +  new Random().nextInt(1000));
					logTaskProducter.push(item);
				}				
			}.start();			
		}	
		/*
		for (int j = 0; j < 50; j++) {
			new Thread() {
				public void run() {
					Items item = new Items();
					item.setName("ORDER" +  new Random(1000));
					orderTaskProducter.push(item);
				}				
			}.start();
			
		}	*/
		
//		Jedis jedis = RedisClient.getJedis();
//		RedisClient.setStr("a", "1", 100000);
//		System.out.println(RedisClient.getStr("a"));
////		jedis.get("a");
////		jedis.close();
//		jedis.quit();
////		jedis.disconnect();
////		RedisClient.getJedis();
		
	}
}



