package test.thread;

public class ThreadStop extends Thread{

	private static volatile boolean stopFlag = false;

	@Override
	public void run() {
		while(!stopFlag) {
//			System.out.println("I am running！");
//			System.out.println("over！");
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		ThreadStop threadStop = new ThreadStop();
		threadStop.start();
		Thread.sleep(300);
//		stopFlag = true;
		
		threadStop.wait();
		Thread.sleep(5000);
		threadStop.notify();
//		threadStop.notifyAll();
		
		
	}
	
	
}
