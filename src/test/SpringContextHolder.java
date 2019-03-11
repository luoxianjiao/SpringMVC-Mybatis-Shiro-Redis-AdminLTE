package test;

import javax.annotation.Resource;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


/**
 * 获取Spring的ApplicationContext的几种方式
 * 1. 直接注入(使用 @Resource 注解)  见ItemController  queryItems方法
 * 2. 实现ApplicationContextAware接口（推荐）
 * @author xianjiao.luo
 *
 */
public class SpringContextHolder implements ApplicationContextAware {
	
	public static ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		SpringContextHolder.applicationContext = applicationContext;
		
	}

	public void get() {
		
		HelloWorld helloWorld = (HelloWorld) applicationContext.getBean("helloWorld");
		System.out.println(helloWorld.getMessage());
	}
	
	public static void main(String[] args) {
		
		SpringContextHolder contextHolder = new SpringContextHolder();
		
		contextHolder.get();
	}
	
	
}
