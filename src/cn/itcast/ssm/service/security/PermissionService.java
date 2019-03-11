package cn.itcast.ssm.service.security;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.pagehelper.PageInfo;

import cn.itcast.ssm.po.RolePermissionAllocationBo;
import cn.itcast.ssm.po.UPermission;
import cn.itcast.ssm.po.UPermissionBo;

public interface PermissionService {

	int deleteByPrimaryKey(Long id);

	UPermission insert(UPermission record);  
	
	int insertSelective(UPermission record);

    UPermission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UPermission record);

    int updateByPrimaryKey(UPermission record);
    
  //根据用户ID查询权限（permission），放入到Authorization里。
  	Set<String> findPermissionByUserId(Long userId);
  	
  	/**
  	 * 分页查询权限列表
  	 * @param findContent
  	 * @param pageNum
  	 * @param pageSize
  	 * @return
  	 * @throws Exception
  	 */
  	PageInfo<UPermission> queryByPage(String findContent, Integer pageNum, Integer pageSize) throws Exception;

  	void deletePermissionByIds(String ids) throws Exception;
  	  	
  	
  	/**
  	 * 根据角色查询权限，及该角色可分配的权限
  	 * @param roleId
  	 * @return
  	 */
  	List<UPermissionBo> selectPermissionByRoleId(Long roleId);
  	
  	/**
  	 * 分页查询角色权限列表
  	 * @param findContent
  	 * @param pageNum
  	 * @param pageSize
  	 * @return
  	 * @throws Exception
  	 */
  	PageInfo<RolePermissionAllocationBo> queryRoleAndPermissionByPage(String findContent, Integer pageNum, Integer pageSize) throws Exception;
  	
  	/**
  	 * 分配权限
  	 * @param roleId
  	 * @param permissionIds
  	 * @throws Exception
  	 */
  	void addPermissionToRole(Long roleId, String permissionIds) throws Exception;
  	

}
