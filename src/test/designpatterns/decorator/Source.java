package test.designpatterns.decorator;
/**
 * 被装饰的类
 * @author Administrator
 *
 */
public class Source implements Sourceable {

	@Override
	public void method() {
		// TODO Auto-generated method stub
		System.out.println("the original method!");
	}

}
