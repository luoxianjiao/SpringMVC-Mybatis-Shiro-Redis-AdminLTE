package test.thread;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.TreeSet;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class ThreadPoolTest {

	
	public static void main(String[] args) {
//		ThreadPoolExecutor 
//		ExecutorService nThreads = Executors.newFixedThreadPool(1);
//		
		Executors.newSingleThreadExecutor();
		Executors.newCachedThreadPool();
		ExecutorService executorService = Executors.newScheduledThreadPool(1);
		ScheduledExecutorService schedulService = Executors.newScheduledThreadPool(1);
		schedulService.schedule(new Runnable() {			
			@Override
			public void run() {
				System.out.println("delay 3 sec");
				
			}
		}, 3, TimeUnit.SECONDS);
		System.out.println(Integer.MAX_VALUE);
		List list  =  new ArrayList<>();
//		Arrays.asList()
		Collections.synchronizedList(new ArrayList<>());
		
		List copyOnWriteArrayList = new CopyOnWriteArrayList<>();
//		copyOnWriteArrayList.add(null);//CopyOnWriteArrayList使用ReentrantLock锁来实现
//		Collections.synchronizedList(null).add(null);//synchronizedList使用的是synchronized锁来实现
		
		
		Set<Integer> s = new HashSet<Integer>();
		
		s.add(1);
		
		Set<Integer> treeS = new TreeSet<Integer>();
		treeS.add(1);
		treeS.add(2);
		treeS.add(3);
		treeS.add(4);
		treeS.add(5);
		treeS.add(6);
		treeS.add(7);
		treeS.add(8);
		treeS.add(9);
		treeS.add(10);
		
		Map a = new HashMap();
		
		Vector v = new Vector<>();
		//具有HashSet的查询速度，且内部使用链表维护元素的顺序(插入的次序)。于是在使用迭代器遍历Set时，结果会按元素插入的次序显示。
		Set<Integer>  linkedHashSet = new LinkedHashSet<>();
		linkedHashSet.add(1);		
		Map b = new ConcurrentHashMap<>();
		
		//重入锁
		//重入锁指的是当前线程成功获取锁后，如果再次访问该临界区，则不会对自己产生互斥行为。
		//Java中对ReentrantLock和synchronized都是可重入锁，
		//synchronized由jvm实现可重入即使，ReentrantLock都可重入性基于AQS实现
		//ReetrantLock重入锁的最主要逻辑就锁判断上次获取锁的线程是否为当前线程:current == getExclusiveOwnerThread()
		
		//ReentrantLock是基于AQS实现的，底层通过操作“同步状态”来获取锁
		//ReentrantLock提供非公平锁和公平锁两种模式
		//非公平锁：当锁状态为可用时，不管在当前锁上是否有其他线程在等待，新近线程都有机会抢占锁。
		ReentrantLock reentrantLock = new ReentrantLock();//默认非公平锁
		//公平锁：指当多个线程尝试获取锁时，成功获取锁的顺序与请求获取锁的顺序相同
		//由AQS可知，如果当前线程获取锁失败就会被加入到AQS同步队列中，那么，如果同步队列中的节点存在前驱节点，
		//也就表明存在线程比当前节点线程更早的获取锁，故只有等待前面的线程释放锁后才能获取锁。
		ReentrantLock fairReentrantLock = new ReentrantLock(true);//初始化为公平锁
		
		//AQS底层同步工具类(队列同步器) 
		AbstractQueuedSynchronizer abstractQueuedSynchronizer = new AbstractQueuedSynchronizer() {
			
		};
		//同步队列是AQS很重要的组成部分，它是一个双端队列，遵循FIFO原则，
		//主要作用是用来存放在锁上阻塞的线程，
		//当一个线程尝试获取锁时，如果已经被占用，那么当前线程就会被构造成一个Node节点假如到同步队列的尾部，队列的头节点是成功获取锁的节点，当头节点线程是否锁时，会唤醒后面的节点并释放当前头节点的引用。
		
		
		
		
		
	}
}
