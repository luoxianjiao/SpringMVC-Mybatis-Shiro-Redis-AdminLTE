package test.thread;

class ThreadB extends Thread {

	
	public ThreadB(String name) {
		super(name);		
	}

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + " run ");
		//死循环，不断运行
		while (true) {  //  这个线程与主线程无关，无 synchronized(不影响主线程)
			
		}
	}
	
}
/**
 * 注意：
 * 1、sleep方法是Thread类的静态方法本地（static native）方法；wait则是Object类的本地方法
 * 2、按1这样设计的原因第一是：sleep的目的是让当前线程休眠，不涉及对象锁且不需要获取对象锁，所以是线程类方法；
 *    第二wait是让获得对象锁的线程实现等待，前提是要先获取到对象的锁，所以是类的方法。
 * 3、释放锁：wait可以释放当前线程某个对象锁的持有，而sleep不会
 * 4、线程切换：
 * 因为 sleep 是让当前线程休眠，不涉及到对象类，也不需要获得对象的锁，所以是线程类的方法
 * @author xianjiao.luo
 *
 */
public class ThreadWaitTimeout {

	
	public static void main(String[] args) {
		ThreadB t1 = new ThreadB("t1");
		synchronized (t1) {
			try {
				//启动线程1
				System.out.println(Thread.currentThread().getName() + " start t1");
				t1.start();
				//主线程等待t1通过notify()唤醒 或 notifyAll() 唤醒，或超过3000ms延时；然后被唤醒
				System.out.println(Thread.currentThread().getName() + " call wait");
				t1.wait(3000);//3秒延时后，可被唤醒
				System.out.println(Thread.currentThread().getName() + " continue");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}		
	}
}
