package test.thread;

import java.util.LinkedList;
import java.util.Queue;

/*
 * 生产者消费者模型
 * 示例java的wait和notify方法使用
 * 生产者线程和消费者线程使用wait/notifyAll方法传递共享对象
 * 注意：
 * 1、wait()、notify/notifyAll() 方法是Object的本地final方法，无法被重写
 * 2、wait()使当前线程阻塞，前提是 必须先获得锁，一般配合synchronized 关键字使用，
 *    即，一般在synchronized 同步代码块里使用 wait()、notify/notifyAll() 方法
 * 3、 由于 wait()、notify/notifyAll() 在synchronized 代码块执行，说明当前线程一定是获取了锁的。
 * 4、wait() 需要被try catch包围，中断也可以使wait等待的线程唤醒
 * 5、notify 和wait 的顺序不能错，如果A线程先执行notify方法，B线程在执行wait方法，那么B线程是无法被唤醒的。
 * 6、notify 和 notifyAll的区别
		notify方法只唤醒一个等待（对象的）线程并使该线程开始执行。所以如果有多个线程等待一个对象，这个方法只会唤醒其中一个线程，选择哪个线程取决于操作系统对多线程管理的实现。notifyAll 会唤醒所有等待(对象的)线程，尽管哪一个线程将会第一个处理取决于操作系统的实现。如果当前情况下有多个线程需要被唤醒，推荐使用notifyAll 方法。比如在生产者-消费者里面的使用，每次都需要唤醒所有的消费者或是生产者，以判断程序是否可以继续往下执行。
   7、在多线程中要测试某个条件的变化，使用if 还是while？
	　要注意，notify唤醒沉睡的线程后，线程会接着上次的执行继续往下执行。所以在进行条件判断时候，可以先把 wait 语句忽略不计来进行考虑，显然，要确保程序一定要执行，并且要保证程序直到满足一定的条件再执行，要使用while来执行，以确保条件满足和一定执行。
 * 
 */
public class ThreadWaitTest {

	public static class Producer extends Thread {
		Queue<Integer> queue;
		int maxsize;
		public Producer(Queue<Integer> queue, int maxsize, String name) {			
			this.queue = queue;
			this.maxsize = maxsize;
			this.setName(name);
		}
		@Override
		public void run() {
			while (true) {
				synchronized (queue) {
					 try{
	                      Thread.sleep(500);
	                  } catch (Exception e) {}
					System.out.println(this.getName() + "获得队列的锁");
					//此处不能使用if进行判断等待条件，必须使用while判断等待条件，因为使用if判断的话唤醒后意味着if继续往下走，会跳出if语句块
					//但是notifyAll只负责唤醒线程，不保证条件一定满足
//					if (queue.size() == maxsize) {
					while (queue.size() == maxsize) {
						System.out.println("队列已满，生产者" + this.getName() + "等待");
						try {
							queue.wait();//释放queue对象锁的持有
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}						
					}
					int num = (int) (Math.random()*100);
					queue.offer(num);
					System.out.println("生产一个数据：" + num);
					queue.notifyAll();//唤醒在queue对象等待的线程，而不会立即释放锁												
					System.out.println(this.getName() + "退出一次生产过程");//
				}
			}
		}		
	}
	
	public static class Consumer extends Thread {
		Queue<Integer> queue;
		int maxsize;
		public Consumer(Queue<Integer> queue, int maxsize, String name) {			
			this.queue = queue;
			this.maxsize = maxsize;
			this.setName(name);
		}
		@Override
		public void run() {
			while (true) {				
				synchronized (queue) {
					try{
	                      Thread.sleep(500);
	                  } catch (Exception e) {}
					System.out.println(this.getName() + "获得队列的锁");
					//此处不能使用if进行判断等待条件，必须使用while判断等待条件，因为使用if判断的话唤醒后意味着if继续往下走，会跳出if语句块
					//但是notifyAll只负责唤醒线程，不保证条件一定满足
//					if (queue.isEmpty()) {
					while (queue.isEmpty()) {											
						System.out.println("队列为空，消费者" + this.getName() + "等待");
						try {
							queue.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					int num = queue.poll();//如果上面的等待条件使用的if判断，当该线程
					System.out.println(this.getName() + "消费一个元素：" + num);
					queue.notifyAll();//唤醒在queue对象等待的线程，而不会立即释放锁
					System.out.println(this.getName() + "退出一次消费过程！");
					
				}
			}
		}								
	}
	
	public static void main(String[] args) {
		Queue<Integer> queue = new LinkedList<>();
		int maxsize = 2;
		
		Producer producer = new Producer(queue, maxsize, "Producer");
		Consumer consumer1 = new Consumer(queue, maxsize, "consumer1");
		Consumer consumer2 = new Consumer(queue, maxsize, "consumer2");
		Consumer consumer3 = new Consumer(queue, maxsize, "consumer3");
		
		producer.start();
		consumer1.start();
		consumer2.start();
		consumer3.start();
		
		
		
	}
	
}
