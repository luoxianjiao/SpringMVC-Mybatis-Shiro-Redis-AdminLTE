package test.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import cn.itcast.ssm.po.User;




/**
 * JAVA SE 5.0开始引入了Callable和Future
 * @author xianjiao.luo
 *
 */
public class ThreadCreate {

	
	static class Thread1 extends Thread {

		@Override
		public void run() {
			System.out.println("Thread1 runing ！！！");			
		}
		
	}
	
	static class Thread2 implements Runnable {

		@Override
		public void run() {
			System.out.println("Thread2 runing ！！！");		
		}		
	}
	
	static class Thread3<T> implements Callable<T> {

		@SuppressWarnings("unchecked")
		@Override
		public T call() throws Exception {
			System.out.println("Thread3 runing ！！！");
			User user = new User();
			user.setId(1);
			user.setSex("man");
			return (T) user;
		}
		
	}
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		Thread t1 = new Thread1();
		Thread t2 = new Thread(new Thread2());		
		FutureTask<User> ft = new FutureTask<User>(new Thread3<User>());
		Thread t3 = new Thread(ft);
		
		t1.start();
		t2.start();
		t3.start();
		System.out.println(ft.get());
	}
}
