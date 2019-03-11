package cn.itcast.ssm.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.itcast.ssm.enums.EnumUserStatus;
import cn.itcast.ssm.po.UMenu;
import cn.itcast.ssm.po.UUser;
import cn.itcast.ssm.service.security.MenuService;
import cn.itcast.ssm.service.security.UUserService;
import cn.itcast.ssm.shiro.token.manager.TokenManager;
import cn.itcast.ssm.utils.StringUtils;

/**
 * 登录注册管理Controller
 * @author xianjiao.luo
 *
 */
@Controller
public class LoginController {
	
	private static Logger logger = Logger.getLogger(LoginController.class);
	
	@Autowired
	private UUserService userService;
	
	@Autowired
	private MenuService menuService;
	
	@RequestMapping(value="/login")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws Exception {		
		ModelAndView modelAndView = new ModelAndView();								 		
//		modelAndView.setViewName("redirect:/items/queryItems.action");
		modelAndView.setViewName("login");
		return modelAndView;
	}
	
	/**
	 * shiro认证处理顺序依次为（与实际声明顺序无关）：
		RequiresRoles 
		RequiresPermissions 
		RequiresAuthentication 
		RequiresUser 
		RequiresGuest
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/main")
	@RequiresRoles("admin")
	public ModelAndView main(HttpServletRequest request, HttpServletResponse response) throws Exception {		
		ModelAndView modelAndView = new ModelAndView();								 		
//		modelAndView.setViewName("redirect:/items/queryItems.action");
		modelAndView.setViewName("/main_bak");
		return modelAndView;
	}
	
	/*
	 * 可供外部测试
	 */
	@RequestMapping(value="/loginSubmit",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> loginSubmit(UUser uuser,Boolean rememberMe,HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			uuser = TokenManager.login(uuser,rememberMe);
			resultMap.put("status", 0);
			resultMap.put("msg", "登陆成功");
			SavedRequest savedRequest = WebUtils.getSavedRequest(request);
			String url = null ;
//			if(null != savedRequest){
//				url = savedRequest.getRequestUrl();
//			}
			url = request.getContextPath() + "/main.action";
			//如果登录之前没有地址，那么就跳转到首页。
			if(StringUtils.isBlank(url)){
				url = request.getContextPath() + "/main.action";
			}
			//跳转地址
			resultMap.put("back_url", url);
		} catch (DisabledAccountException e) {
			logger.error("帐号已经禁用",e);
			resultMap.put("status", -1);
			resultMap.put("msg", "帐号已经禁用。");
		} catch (Exception e) {
			logger.error("帐号或密码错误",e);
			resultMap.put("status", -2);
			resultMap.put("msg", "帐号或密码错误");
		}	
		return resultMap;					
	}
	
	@RequestMapping(value="/register")
	public ModelAndView register(HttpServletRequest request, HttpServletResponse response) throws Exception {		
		ModelAndView modelAndView = new ModelAndView();								 			
		modelAndView.setViewName("register");
		return modelAndView;
	}
	
	@RequestMapping(value="registerSubmit",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> registerSubmit(String vcode, UUser uUser) {
		Map<String, Object> resultMap = new HashMap<>();		
		/*if(!VerifyCodeUtils.verifyCode(vcode)){
			resultMap.put("message", "验证码不正确！");
			return resultMap;
		}*/
		try {
			String email =  uUser.getEmail();	
			if(StringUtils.isBlank(email)) {
				resultMap.put("status", -1);
				resultMap.put("message", "请输入Email！");
				return resultMap; 
			}
			UUser user = userService.findUserByEmail(email);
			if(null != user){
				resultMap.put("status", -1);
				resultMap.put("message", "帐号|Email已经存在！");
				return resultMap;
			}
			Date date = new Date();
			uUser.setCreate_time(date);
			uUser.setLast_login_time(date);
			//把密码md5
			uUser = userService.md5Pswd(uUser);
			//设置有效
			uUser.setStatus(EnumUserStatus.VALID.getCode());		
			uUser = userService.insert(uUser);
			logger.info("注册插入数据完成");
			uUser = TokenManager.login(uUser, Boolean.FALSE);
		} catch (Exception e) {
			logger.error("注册失败",e);
			resultMap.put("msg", "注册失败！");
			resultMap.put("status", -1);
			return resultMap; 
		}
		logger.info("注册后，登录完成");
		resultMap.put("msg", "注册成功！");
		resultMap.put("status", 0);
		return resultMap;
	}
	
	@RequestMapping(value="/menu/getList")
	@ResponseBody
	public List<UMenu> getUserMenus() throws Exception{
		Long userId = TokenManager.getToken().getUserId();
		List<UMenu> menuList = menuService.queryUserMenus(userId);
		return menuList;		
	}
	
	/*@RequestMapping(value="/logout")
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		request.getSession(true).invalidate();	
		modelAndView.setViewName("redirect:/items/queryItems.action");
		return modelAndView;		
	}*/
	
	/**
	 * 退出
	 * @return
	 */
	@RequestMapping(value="logout",method =RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> logout(){
		Map<String, Object> resultMap = new HashMap<>();
		try {
			TokenManager.logout();
			resultMap.put("status", 200);
		} catch (Exception e) {
			resultMap.put("status", 500);
			logger.error("errorMessage:" + e.getMessage());
			logger.error("退出出现错误",e);			
		}
		return resultMap;
	}
}
