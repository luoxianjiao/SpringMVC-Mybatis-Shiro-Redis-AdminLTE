package cn.itcast.ssm.interceptor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoginInterceptor implements HandlerInterceptor {

	private static Logger logger = Logger.getLogger(LoginInterceptor.class); 
	private Long start;
	private Long end;
	private String viewName;
	private Map<String, String> param = new HashMap<String, String>();
	//进入Handler方法之前执行
	//一般用于身份验证、身份授权
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		//return false表示拦截，不向下执行
		//return true表示放行
		String basePath = request.getContextPath();
        request.setAttribute("basePath", basePath);
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
		
		
	}

	

	
	
	

}
