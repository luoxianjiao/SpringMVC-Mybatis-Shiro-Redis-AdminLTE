package test.thread;

import java.io.IOException;

/**
 * 守护线程
 * @author xianjiao.luo
 *
 */
public class DaemonThread {

	private static class UserThread implements Runnable {

		@Override
		public void run() {
			try {
				for (int i = 0; ; i++) {
					Thread.sleep(1000);
					System.out.println(i);
				}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}						
		}		
	}
	public static void main(String[] args) throws IOException {
		Thread userThread = new Thread(new UserThread());
		userThread.setDaemon(true);
		userThread.start();
		System.in.read();
	}
}
