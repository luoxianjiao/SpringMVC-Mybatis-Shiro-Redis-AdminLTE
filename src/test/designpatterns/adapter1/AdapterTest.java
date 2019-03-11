package test.designpatterns.adapter1;
/**
 * Targetable接口的实现类就具有了Source类的功能
 * 类的适配器模式
 * @author Administrator
 *
 */
public class AdapterTest {
	public static void main(String[] args) {
		Targetable target = new Adapter();
		target.method1();
		target.method2();
	} 
}
