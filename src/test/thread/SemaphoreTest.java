package test.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 同一时间只有5个线程运行，使用Semaphore
 * @author xianjiao.luo
 *
 */
public class SemaphoreTest {

	public static void main(String[] args) {
		ExecutorService executorService = Executors.newCachedThreadPool();		
		final Semaphore semaphore = new Semaphore(5); //
		//模拟20个客户端访问
		for(int i = 0; i < 10; i++) {
			final int No = i;
			Runnable runnable = new Runnable() {				
				@Override
				public void run() {
					//获取许可
					try {
						semaphore.acquire();
						System.out.println("Accessing:" + No);
						Thread.sleep(300);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						//释放许可，不释放的话，只有5个线程能在同一时间执行
						semaphore.release();
					}					
				}
			};			
			executorService.execute(runnable);
			
		}
	}
	
	
	
}
