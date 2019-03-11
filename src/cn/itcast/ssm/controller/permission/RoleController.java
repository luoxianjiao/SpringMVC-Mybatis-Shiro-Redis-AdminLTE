package cn.itcast.ssm.controller.permission;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;

import cn.itcast.ssm.po.URole;
import cn.itcast.ssm.po.URoleBo;
import cn.itcast.ssm.po.UUser;
import cn.itcast.ssm.po.UserRoleAllocationBo;
import cn.itcast.ssm.service.security.RoleService;

/**
 * 角色相关控制器
 * @author xianjiao.luo
 *
 */
@Controller
@RequestMapping("role")
public class RoleController {

	@Autowired
	private RoleService roleService;

	private static Logger logger = Logger.getLogger(RoleController.class);
	
	@RequestMapping(value="roleList")
	public ModelAndView toRoleList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView("role/roleList");										 		
		return modelAndView;
	}
	
	/**
	 * 角色分配列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="allocation")
	public ModelAndView allotRoleList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView("role/allocation");										 		
		return modelAndView;
	}
	
	/**
	 * 分页查询角色列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/getRoleList",method=RequestMethod.POST)
	public Map<String, Object> getRoleList(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer draw = Integer.valueOf(request.getParameter("draw"));
		Integer start = Integer.valueOf(request.getParameter("start"));
		Integer length = Integer.valueOf(request.getParameter("length"));
		String findContent = request.getParameter("findContent");
		int pageNum = start / length + 1;
		int pageSize = length; 
		PageInfo<URole> pageInfo = roleService.queryByPage(findContent, pageNum, pageSize);
//		map.put("pageInfo", pageInfo);
		map.put("draw", draw);
		map.put("recordsTotal", pageInfo.getTotal());
		map.put("recordsFiltered", pageInfo.getTotal());		
		map.put("data", pageInfo.getList());		
		return map;
	}
	
	@RequestMapping(value="addRole",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addRole(URole role) {
		Map<String,Object> resultMap = new HashMap<>();
		try {
			roleService.insertSelective(role);
			resultMap.put("status", 0);
			resultMap.put("msg", "添加成功");
			logger.info("添加角色成功！");
		} catch (Exception e) {
			resultMap.put("status", -1);
			resultMap.put("msg", "添加失败");
			logger.error("添加角色失败！",e);
		}
		return resultMap; 
	}
	
	@RequestMapping(value="deleteRoleByIds",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteRoleByIds(String ids) {
		Map<String,Object> resultMap = new HashMap<>();
		try {
			roleService.deleteRoleByIds(ids);
			resultMap.put("status", 0);
			resultMap.put("msg", "删除角色成功");
		} catch (Exception e) {
			resultMap.put("status", -1);
			resultMap.put("msg", "删除角色失败");
			logger.error("删除角色异常");
		}
		return resultMap; 
	}
	
	/**
	 * 分页查询用户角色分配列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/getRoleAllocationList",method=RequestMethod.POST)
	public Map<String, Object> getRoleAllocationList(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer draw = Integer.valueOf(request.getParameter("draw"));
		Integer start = Integer.valueOf(request.getParameter("start"));
		Integer length = Integer.valueOf(request.getParameter("length"));
		String findContent = request.getParameter("findContent");
		int pageNum = start / length + 1;
		int pageSize = length; 
		PageInfo<UserRoleAllocationBo> pageInfo = roleService.queryUserRoleAllocationByPage(findContent, pageNum, pageSize);		
		map.put("draw", draw);
		map.put("recordsTotal", pageInfo.getTotal());
		map.put("recordsFiltered", pageInfo.getTotal());		
		map.put("data", pageInfo.getList());		
		return map;
	}
	
	/**
	 * 查询指定用户拥有的角色及所有角色
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/getRoleByUserId")
	public List<URoleBo> getRoleByUserId(Long userId) throws Exception {
		return roleService.selectRoleByUserId(userId);
	}
	
	/**
	 * 分配角色
	 * @param userId
	 * @param roleIds
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/addRoleToUser",method=RequestMethod.POST)
	public Map<String, Object> addRoleToUser(Long userId, String roleIds) {
		Map<String,Object> resultMap = new HashMap<>();
		try {
			roleService.addRoleToUser(userId, roleIds);
			resultMap.put("status", 0);
			resultMap.put("msg", "分配角色成功");
		} catch (Exception e) {
			resultMap.put("status", -1);
			resultMap.put("msg", "分配角色异常");
		}
		return resultMap;
	}
	
	
	
	
	
	
	
	
	
	
}
