package test.designpatterns;

/**
 * 单例模式
 * @author xianjiao.luo
 *
 */
public class Singleton {

	//私有静态示例
	private volatile static Singleton instance = null;
	private Singleton() {};
	//静态方法	
	public static Singleton getInstance() {
		//使用双重检查，保证线程安全，同事减小锁的粒度，对象存在时不需要锁
		if(instance == null) { //这里判空，进入getInstance()方法时，不为空时不需要锁，则可降低锁粒度
			synchronized (Singleton.class) {
				if(instance == null) {
					instance = new Singleton();
				}
			}
		}		
		return instance;
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			Singleton singleton = Singleton.getInstance();
			
		}
		
	}
	
}
