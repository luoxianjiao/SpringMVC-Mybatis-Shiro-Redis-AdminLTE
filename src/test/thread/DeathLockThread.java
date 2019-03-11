package test.thread;

/**
 * 死锁示例，以下线程1和线程2相互得到锁1、锁2，然后线程1等待线程2释放锁2，线程2等待线程1释放锁1，两者各不相互，这样形成死锁。
 * 解决
 * @author xianjiao.luo
 *
 */
public class DeathLockThread {

	private static Object lock1 = new Object();
	private static Object lock2 = new Object();
	
	public static void main(String[] args) {
		
		new Thread() {
			@Override
			public void run() {
				synchronized (lock1) {
					System.out.println("thread1 get lock1");
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					synchronized (lock2) {
						System.out.println("thread2 get lock2");
					}
				}
				System.out.println("thread1 end");
			}
			
		}.start();
		
		new Thread() {

			@Override
			public void run() {
				synchronized (lock2) {
					System.out.println("thread2 get lock2");
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					synchronized (lock1) {
						System.out.println("thread2 get lock1");
					}
				}
				System.out.println("thread2 end");
			}
			
			
		}.start();
	}
}
