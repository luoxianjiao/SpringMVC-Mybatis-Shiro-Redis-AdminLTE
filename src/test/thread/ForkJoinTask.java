package test.thread;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;


/**
 * Fork/Join框架实战
 * Fork/Join框架中实际执行任务类，有以下两种实现，一般继承这两种实现类即可
 * 1、RecursiveAction:用于无结果返回的子任务；
 * 2、RecursiveTask：用于有结果返回的子任务；本实例需要返回计算结果所以继承RecursiveTask
 * @author xianjiao.luo
 *
 */
public class ForkJoinTask extends RecursiveTask<Long>{

	private static final long MAX = 1000000000L;
	private static final long THRESHOLD = 10000L;
	private long start;
	private long end;
	
	
	public ForkJoinTask(long start, long end) {
		super();
		this.start = start;
		this.end = end;
	}
	public static void main(String[] args) {
		test();
		System.out.println("--------------------");
		testForkJoin();
	}
	private static void test() {
		System.out.println("test");
		long sum  = 0L;
		long start = System.currentTimeMillis();
		for (long i = 0; i <= MAX ; i++) {
			sum += i;
		}
		System.out.println(sum);
		System.out.println(System.currentTimeMillis() - start + "ms");
	}

	private static void testForkJoin() {
		System.out.println("testForkJoin");
		long start = System.currentTimeMillis();
		ForkJoinPool forkJoinPool = new ForkJoinPool();
		long sum = forkJoinPool.invoke(new ForkJoinTask(1, MAX));
		System.out.println(sum);
		System.out.println(System.currentTimeMillis() - start + "ms");
		
		
	}
	@Override
	protected Long compute() {
//		System.out.println("compute");
		long sum = 0L;
		if(end - start <=THRESHOLD) {
			for (long i = start; i <= end; i++) {
				sum += i;
			}
			return sum;
		} else {
			long mid = (start + end) / 2;
			ForkJoinTask task1 = new ForkJoinTask(start, mid);
			//每个子任务在调用fork方法时，又会进入computer方法，看当前子任务是否需要继续分割成孙任务，如果不需要继续分割，则执行当前子任务并返回结果
			//使用join方法会阻塞并等待子任务执行完并得到结果
			task1.fork();			
			ForkJoinTask task2 = new ForkJoinTask(mid+1, end);
			task2.fork();
			return task1.join() + task2.join();
		}
		 
	}
	
	
	
}
