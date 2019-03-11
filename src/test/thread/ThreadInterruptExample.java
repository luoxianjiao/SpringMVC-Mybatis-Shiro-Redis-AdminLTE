package test.thread;


/**
 * 当代码调用中须要抛出一个InterruptedException, 你可以选择把中断状态复位, 也可以选择向外抛出InterruptedException, 由外层的调用者来决定. 
不是所有的阻塞方法收到中断后都可以取消阻塞状态, 输入和输出流类会阻塞等待 I/O 完成，但是它们不抛出 InterruptedException，而且在被中断的情况下也不会退出阻塞状态. 
尝试获取一个内部锁的操作（进入一个 synchronized 块）是不能被中断的，但是 ReentrantLock 支持可中断的获取模式即 tryLock(long time, TimeUnit unit)。
 * @author xianjiao.luo
 *
 */
public class ThreadInterruptExample extends Thread{

	
	volatile boolean stopFlag = false;

	@Override
	public void run() {
		while (!stopFlag) {
			System.out.println("t is running !");			
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			//接收到一个中断异常（Interrupted），从而提前结束阻塞状态
			e.printStackTrace();
		}
		
		System.out.println("Thread exiting under request...");  
	}
	
	public static void main(String[] args) throws Exception {
		
		ThreadInterruptExample t = new ThreadInterruptExample();
		System.out.println("start thread t... !");
		t.start();	
		Thread.sleep(3000);
		
		System.out.println("Asking thread t... to stop...");		
		t.stopFlag = true;		
		t.interrupt();		
		Thread.sleep(3000);
		System.out.println("stop application...");
		System.exit(0);
						
	}
	
}
