package test.designpatterns.adapter2;

import test.designpatterns.adapter1.Source;
import test.designpatterns.adapter1.Targetable;
/**
 * 对象的适配器模式
 * @author Administrator
 *
 */
public class AdapterTest {
	public static void main(String[] args) {
		Source source = new Source();
		Targetable target = new Wrapper(source);
		target.method1();
		target.method2();
	}
	
}
