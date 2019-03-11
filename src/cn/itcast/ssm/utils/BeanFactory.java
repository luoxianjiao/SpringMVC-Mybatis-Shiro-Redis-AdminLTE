package cn.itcast.ssm.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class BeanFactory implements ApplicationContextAware{

	public static ApplicationContext context;
	@SuppressWarnings("static-access")
	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		this.context = context;		
	}
	
	public static Object getBean(String beanName) {
		return context.getBean(beanName);
	}

	
}
