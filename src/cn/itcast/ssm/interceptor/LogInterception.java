package cn.itcast.ssm.interceptor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LogInterception implements HandlerInterceptor {

	private static Logger logger = Logger.getLogger(LogInterception.class); 
	private NamedThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<Long>("Watch-startTime");	
	private String viewName;
	private Map<String, String> param = new HashMap<String, String>();
	//进入Handler方法之前执行
	//一般用于身份验证、身份授权
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		//return false表示拦截，不向下执行
		//return true表示放行
		long startTime = System.currentTimeMillis();
		startTimeThreadLocal.set(startTime);		
		Map<String, String[]> parameters = request.getParameterMap();		
		Iterator<String> it = parameters.keySet().iterator();
		logger.info("Receive [" + request.getMethod() + "] request !");
		while(it.hasNext()) {
			String key = it.next();
			String value = request.getParameter(key);
			param.put(key, value);
		}
		logger.info("Request data:" + param);
		param.clear();
		return true;
	}
	
	//进入Handler之后，返回ModelAndView之前执行
	//应用场景：需将公共的模型数据，例如左侧的菜单导航数据，在这里传到视图，也可以在这里统一指定视图
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler, ModelAndView mv) throws Exception {
		if(mv == null) {
			viewName = "Ajax request!";
		} else {
			viewName = mv.getViewName();
		}
		logger.info("ViewName:" + viewName);  	
	}
	
	//执行Handler完成，执行此方法
	//应用场景：统一异常处理，统一日志处理
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		long endTime = System.currentTimeMillis();
		long startTime = startTimeThreadLocal.get();
		if(ex != null) {
			logger.error(ex.getMessage(), ex);			
		}
		
		logger.info("Response [" + request.getRequestURI() + "] request take "+(endTime-startTime) + "ms");
	}

	

	
	
	

}
