package cn.itcast.ssm.controller.user;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.itcast.ssm.po.UUser;
import cn.itcast.ssm.service.security.UUserService;

import com.github.pagehelper.PageInfo;


/**
 * 用户管理Controller
 * @author xianjiao.luo
 *
 */
@Controller
@RequestMapping("user")
public class UserManageController {

	private static Logger logger = Logger.getLogger(UserManageController.class);
	
	@Autowired
	private UUserService userService;
	
	@RequestMapping(value="/userList")
	public ModelAndView userList() {
		ModelAndView modelAndView = new ModelAndView("user/userList");
		return modelAndView;		
	}
	
	@ResponseBody
	@RequestMapping(value="/getUserList",method=RequestMethod.POST)
	public Map<String, Object> getUserList(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer draw = Integer.valueOf(request.getParameter("draw"));
		Integer start = Integer.valueOf(request.getParameter("start"));
		Integer length = Integer.valueOf(request.getParameter("length"));
		int pageNum = start / length + 1;
		int pageSize = length; 
		PageInfo<UUser> pageInfo = userService.queryByPage(pageNum, pageSize);
//		map.put("pageInfo", pageInfo);
		map.put("draw", draw);
		map.put("recordsTotal", pageInfo.getTotal());
		map.put("recordsFiltered", pageInfo.getTotal());		
		map.put("data", pageInfo.getList());
		System.out.println(request.getParameter("opportunityNO"));
		System.out.println(request.getParameter("opportunityNO1"));
		return map;
	}
	
	
	
}
