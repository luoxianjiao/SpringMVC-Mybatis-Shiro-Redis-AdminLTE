package test.thread;

/**
 * http://blog.csdn.net/canot/article/details/51087772
 * 注意：
 * 1、没有任何语言方面的需求要求一个被中断的程序应该终止。中断一个线程只是为了引起该线程的注意，被中断线程可以决定如何应对中断
 * 2、interrupt中断的是线程的某一部分业务逻辑，前提是线程需要检查自己的中断状态(isInterrupted())。
 * 3、但是当线程被阻塞的时候，比如被Object.wait, Thread.join和Thread.sleep三种方法之一阻塞时。调用它的interrput()方法。
 *    可想而知，没有占用CPU运行的线程是不可能给自己的中断状态置位的。这就会产生一个InterruptedException异常。 
 * @author xianjiao.luo
 *
 */
public class ThreadInterrupt extends Thread{

	@Override
	public void run() {
		
		while (true) {
			if(Thread.currentThread().isInterrupted()) {
				System.out.println("someone interrupt me !");
			} else {
				System.out.println("Thread is running !");
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		ThreadInterrupt threadInterrupt = new ThreadInterrupt();
		threadInterrupt.start();
		Thread.sleep(2000);
		threadInterrupt.interrupt();
	}
	
	/******
	 *  //Interrupted的经典使用代码    
    public void run(){    
            try{    
                 ....    
                 while(!Thread.currentThread().isInterrupted()&& more work to do){    
                        // do more work;    
                 }    
            }catch(InterruptedException e){    
                        // thread was interrupted during sleep or wait    
            }    
            finally{    
                       // cleanup, if required    
            }    
    } 
	 * 
	 * 
	 * 
	 * 
	 * ********/
	
	
}
