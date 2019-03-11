package cn.itcast.ssm.controller.permission;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;

import cn.itcast.ssm.po.RolePermissionAllocationBo;
import cn.itcast.ssm.po.UPermission;
import cn.itcast.ssm.po.UPermissionBo;
import cn.itcast.ssm.po.URole;
import cn.itcast.ssm.service.security.PermissionService;
import cn.itcast.ssm.shiro.token.SampleRealm;


/**
 * 权限相关控制器
 * @author xianjiao.luo
 *
 */
@Controller
@RequestMapping("permission")
public class PermissionController {

	@Autowired
	private PermissionService permissionService;
	
	private static Logger logger = Logger.getLogger(PermissionController.class);
	/**
	 * 我的权限页面
	 * @return
	 */
	@RequestMapping(value="myPermission",method=RequestMethod.GET)
	public ModelAndView mypermission(){
		return new ModelAndView("permission/mypermission");
	}
	
	@RequestMapping(value="permissionList",method=RequestMethod.GET)
	public ModelAndView toPermissionList(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView("permission/permissionList");										 		
		return modelAndView;
	}
	
	/**
	 * 权限分配页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="allocation")
	public ModelAndView allotRoleList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView("permission/allocation");										 		
		return modelAndView;
	}
	
	/**
	 * 分页查询权限列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/getPermissionList",method=RequestMethod.POST)
	public Map<String, Object> getUserList(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer draw = Integer.valueOf(request.getParameter("draw"));
		Integer start = Integer.valueOf(request.getParameter("start"));
		Integer length = Integer.valueOf(request.getParameter("length"));
		String findContent = request.getParameter("findContent");
		int pageNum = start / length + 1;
		int pageSize = length; 
		PageInfo<UPermission> pageInfo = permissionService.queryByPage(findContent, pageNum, pageSize);
		map.put("draw", draw);
		map.put("recordsTotal", pageInfo.getTotal());
		map.put("recordsFiltered", pageInfo.getTotal());		
		map.put("data", pageInfo.getList());		
		return map;
	}
	
	@RequestMapping(value="addPermission",method=RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("/permission/addPermission.action")
	public Map<String,Object> addPermission(UPermission permission) {
		Map<String,Object> resultMap = new HashMap<>();
		try {
			permissionService.insertSelective(permission);
			resultMap.put("status", 0);
			resultMap.put("msg", "添加权限成功");
			logger.info("添加权限成功！");
		} catch (Exception e) {
			resultMap.put("status", -1);
			resultMap.put("msg", "添加权限失败");
			logger.error("添加权限失败！",e);
		}
		return resultMap; 
	}
	
	/**
	 * 更新权限
	 * @param permission
	 * @return
	 */
	@RequestMapping(value="updatePermission", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePermission(UPermission permission) {
		Map<String, Object> resultMap = new HashMap<>();		
		try {
			permissionService.updateByPrimaryKey(permission);
			resultMap.put("status", 0);
			resultMap.put("msg", "更新权限成功");
			logger.info("更新权限成功！");
		} catch (Exception e) {
			resultMap.put("status", -1);
			resultMap.put("msg", "更新权限失败");
			logger.error("更新权限失败！",e);
		}
		return resultMap;
	}
	
	/**
	 * 根据ids删除权限
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="deletePermissionByIds",method=RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("/permission/deletePermissionByIds.action")
	public Map<String,Object> deletePermissionByIds(String ids){
		Map<String,Object> resultMap = new HashMap<>();
		try {
			permissionService.deletePermissionByIds(ids);
			resultMap.put("status", 0);
			resultMap.put("msg", "删除权限成功");
		} catch (Exception e) {
			resultMap.put("status", -1);
			resultMap.put("msg", "删除权限失败");
			logger.error("删除角色异常");
		}
		return resultMap;
	}
	

	/**
	 * 分页查询角色权限列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getPermissionAllocationList",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getPermissionAllocationList(HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Integer draw = Integer.valueOf(request.getParameter("draw"));
		Integer start = Integer.valueOf(request.getParameter("start"));
		Integer length = Integer.valueOf(request.getParameter("length"));
		String findContent = request.getParameter("findContent");
		int pageNum = start / length + 1;
		int pageSize = length;
		PageInfo<RolePermissionAllocationBo> pageInfo = permissionService.queryRoleAndPermissionByPage(findContent, pageNum, pageSize);
		resultMap.put("draw", draw);
		resultMap.put("recordsTotal", pageInfo.getTotal());
		resultMap.put("recordsFiltered", pageInfo.getTotal());		
		resultMap.put("data", pageInfo.getList());		
		return resultMap;		
	}
	
	/**
	 * 
	 * @param roleId
	 * @return 根据角色查询权限，及该角色可分配的权限
	 * @throws Exception
	 */
	@RequestMapping(value="/getPermissionByRoleId")
	@ResponseBody
	public List<UPermissionBo> getPermissionByRoleId(Long roleId) throws Exception {
		List<UPermissionBo> list = permissionService.selectPermissionByRoleId(roleId);
		return list;
	}
	
	@RequestMapping(value="/addPermissionToRole", method=RequestMethod.POST)
	@ResponseBody	
	public Map<String, Object> addPermissionToRole(Long roleId, String permissionIds) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			permissionService.addPermissionToRole(roleId, permissionIds);
			resultMap.put("status", 0);
			resultMap.put("msg", "分配权限成功!");
		} catch (Exception e) {
			resultMap.put("status", -1);
			resultMap.put("msg", "分配权限失败!");
		}
		RealmSecurityManager rsm = (RealmSecurityManager)SecurityUtils.getSecurityManager();
		SampleRealm authRealm = (SampleRealm)rsm.getRealms().iterator().next();
		authRealm.clearCachedAuthorizationInfo();
		authRealm.getAuthorizationCache().clear();
		authRealm.getAuthorizationCache();
//		authRealm.
		return resultMap;		
	}
}
