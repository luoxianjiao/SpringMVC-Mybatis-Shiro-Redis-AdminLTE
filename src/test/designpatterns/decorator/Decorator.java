package test.designpatterns.decorator;
/**
 * Decorator是一个装饰类，可以为Source类动态的添加一些功能
 * @author Administrator
 *
 */
public class Decorator implements Sourceable{

	private Sourceable sourceable;
	
	public Decorator(Sourceable sourceable) {
		super();
		this.sourceable = sourceable;
	}

	@Override
	public void method() {
		// TODO Auto-generated method stub
		System.out.println("before decorator!");
		sourceable.method();
		System.out.println("after decorator!");
	}

}
