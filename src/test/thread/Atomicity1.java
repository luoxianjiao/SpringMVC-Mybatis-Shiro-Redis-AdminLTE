package test.thread;

import org.apache.commons.lang3.concurrent.ConcurrentUtils;



/**
 * 使用synchronized关键字 实现多线程“自增操作”原子性
 * @author xianjiao.luo
 *
 */
public class Atomicity1 {

	
	public int inc = 0;
	
	public synchronized void increase() {
		inc++;  //让完整的“自增操作”在一个线程里完成
	}
	
	public static void main(String[] args) {
		final Atomicity1 atomicity1 = new Atomicity1();
		for (int i = 0; i < 10; i++) {
			new Thread() {
				@Override
				public void run() {
					for (int j = 0; j < 1000; j++) {
						atomicity1.increase();
					}
				}				
			}.start();
		}
		while(Thread.activeCount() > 1) {  //保证前面的线程都执行完
			Thread.yield();
		}
		System.out.println(atomicity1.inc);
//		Thread.State.WAITING;
		//dubbo
		
	}
}
