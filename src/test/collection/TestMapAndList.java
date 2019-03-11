package test.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;

public class TestMapAndList {

	
	public static void main(String[] args) {				
		List list = new ArrayList<>(2);
		list.add(1);
		list.add(2);
		list.add(23);
		list.add(24);
		System.out.println(list.size());
		System.out.println();
		Object[] a = {1, 2};
		Object[] b = new Object[10];
		b = list.toArray();
		List synList = Collections.synchronizedList(list);
		Map synMap = Collections.synchronizedMap(null);
		list = Arrays.asList(b);
		
		Hashtable hashtable = new Hashtable<>();
		hashtable.size();
        
	}
}
