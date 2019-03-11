package test.designpatterns.proxy;
/**
 * 代理模式就是多一个代理类出来，替原对象进行一些操作
 * @author Administrator
 *
 */
public class Proxy implements Sourceable{

	private Source source;
	
	public Proxy() {
		super();
		this.source = new Source();
	}
	
	@Override
	public void method() {
		before();
		source.method();
		after();
		
	}
	
	private void after() {
		System.out.println("after proxy!");
	}
	
	private void before() {
		System.out.println("before proxy!");
	}

	
	
	
	

}
