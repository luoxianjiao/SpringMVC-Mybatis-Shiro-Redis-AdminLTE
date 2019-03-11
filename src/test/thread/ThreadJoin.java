package test.thread;

public class ThreadJoin {

	public static void main(String[] args) throws InterruptedException {
		System.out.println("start ！！！");
		
		Thread t1 = new Thread() {
			@Override
			public void run() {
				System.out.println("t1 running !");
			}
		    	 
		};
		
		Thread t2 = new Thread() {

			@Override
			public void run() {
				System.out.println("t2 running !");
			}
			
		};
		
		t1.start();
		//join方法阻塞调用此方法的线程,t1.join()阻塞的是main线程，等t1线程执行完之后main再往下执行
		t1.join();//等待t1线程死去在往下执行
		
		t2.start();
		t2.join();		
				
		System.out.println("end！！！");
			
		
	}
}
