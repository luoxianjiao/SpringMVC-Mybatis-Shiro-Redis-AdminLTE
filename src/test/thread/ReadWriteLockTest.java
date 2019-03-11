package test.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;



public class ReadWriteLockTest {

	static ReentrantLock lock = new ReentrantLock();
	
	public static void main(String[] args) throws InterruptedException {
		ReadWriteLock readWriteLock = new ReentrantReadWriteLock(false); //fair参数可配置是否实现公平锁，默认也是false
		//创建一个线程池
		ExecutorService ex = Executors.newFixedThreadPool(2);
		lock.lock();
		RLLockThread rLLockThread = new RLLockThread();
//		ex.execute(rLLockThread);
		rLLockThread.start();
		Thread.sleep(1000);
		System.out.println("Asking thread rLLockThread... to stop...");		
		rLLockThread.interrupt();
		Thread.sleep(2000);
		lock.unlock();		
		System.out.println("Thread exiting under request...");
		
	}
	
	static class RLLockThread extends Thread {

		@Override
		public void run() {
			System.out.println("t is running");
//			lock.lock();
			try {
				lock.lockInterruptibly();
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			
			System.out.println("is locking");
			if(lock.isLocked()) {
				lock.unlock();
			}
			
			System.out.println("RLLockThread is ending");
			
		}
		
		
		
	}
	
	 
}
