package test.queue;

import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * 非阻塞线程安全列表
 * @author xianjiao.luo
 *
 */
public class ConcurrentLinkedDequeTest {

	
	private static final ConcurrentLinkedDeque<Object> deque = new ConcurrentLinkedDeque();
	public static void main(String[] args) {
		deque.add("a");
		deque.add("b");
		while (true) {
			String x = (String) deque.pollFirst();
			if(x ==null) break;
			System.out.println(x);
			
		}
		
	}
}
