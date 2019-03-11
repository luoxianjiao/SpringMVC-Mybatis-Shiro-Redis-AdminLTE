package test.thread;

/**
 * 并发线程之 原子性   以下为“达不到原子性”的写法
 * @author xianjiao.luo
 *
 */
public class Atomicity {

	public volatile int inc = 0;
	
	public void increase() {
		inc++;
	}
	
	public static void main(String[] args) {
		final Atomicity atomicity = new Atomicity();
		for (int i = 0; i < 10; i++) {
			new Thread() {
				@Override
				public void run() {
					for (int j = 0; j < 1000; j++) {
						atomicity.increase(); 
					}
				}				
			}.start();			
		}
		while(Thread.activeCount() > 1) {   //保证前面的线程都执行完
			Thread.yield();   
		}
		System.out.println(atomicity.inc);
		//输出不一定就10000（10*1000），多数是小于10000
		//根源在于 “自增操作”不是原子性操作，而且volatile不能保证对变量的任何操作都是原子性的。
		//“自增操作”的三个子操作包括1.读取变量的原始值；2.进行加1操作；3.写入主内存
	}	
	
	
}
