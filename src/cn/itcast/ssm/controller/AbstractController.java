package cn.itcast.ssm.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import cn.itcast.ssm.exception.CustomException;

public abstract class AbstractController {
	
	/**
	 * 注解方式 来统一处理异常，有需要异常处理的Controller都继承该类即可
	 * @param response
	 * @param ex
	 * @return
	 */
	@ExceptionHandler
	public ModelAndView resolveException(HttpServletResponse response, Exception ex) {
		CustomException customException =  null;
		if(ex instanceof CustomException) {
			customException = (CustomException) ex;			
		} else {
			customException = new CustomException("未知错误");
		}
		String  message = customException.getMessage();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("message", message);
		modelAndView.setViewName("error");
		return modelAndView;
	}

}
