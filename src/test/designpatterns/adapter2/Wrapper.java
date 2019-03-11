package test.designpatterns.adapter2;

import test.designpatterns.adapter1.Source;
import test.designpatterns.adapter1.Targetable;

/**
 * 这次不继承Source类，而是持有Source类的实例
 * @author Administrator
 *
 */
public class Wrapper implements Targetable {
	private Source source;
	
	public Wrapper(Source source) {
		super();
		this.source = source;
	}

	@Override
	public void method2() {
		System.out.println("this is the targetable method!");
		
	}

	@Override
	public void method1() {
		source.method1();		
	}
	
	
	
	

}
