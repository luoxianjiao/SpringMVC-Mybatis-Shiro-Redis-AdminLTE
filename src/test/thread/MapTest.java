package test.thread;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class MapTest {

	
	public static  ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<String, Integer>();
	public static synchronized void increase() {
		int _val = map.get("a");
		_val++;
		map.put("a", _val);
	}
	public static void main(String[] args) {
		map.put("a", 0);
		for (int i = 0; i < 2; i++) {			
			new Thread() {
				@Override
				public void run() {
					for (int j = 0; j < 1000; j++) {
						increase();
					}
				}				
			}.start();
		}
		while (Thread.activeCount() >1) {
			Thread.yield();
		}
		System.out.println(map.get("a"));
		
	}
}
