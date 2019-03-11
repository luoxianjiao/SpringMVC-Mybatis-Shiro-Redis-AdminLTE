package cn.itcast.ssm.mapper;

import cn.itcast.ssm.po.Items;
import cn.itcast.ssm.po.ItemsCustom;
import cn.itcast.ssm.po.ItemsExample;
import cn.itcast.ssm.po.ItemsQueryVo;
import cn.itcast.ssm.po.RolePermissionAllocationBo;
import cn.itcast.ssm.po.UMenu;
import cn.itcast.ssm.po.UPermissionBo;
import cn.itcast.ssm.po.URoleBo;
import cn.itcast.ssm.po.UUser;
import cn.itcast.ssm.po.UserRoleAllocationBo;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

public interface UUserMapperCustom {
    //商品查询列表
//	public List<ItemsCustom> findItemsList(ItemsQueryVo itemsQueryVo)throws Exception;
	
	public UUser findUserByEmail(String email) throws Exception;
	
	UUser login(Map<String, Object> map);
	
	Set<String> findRoleByUserId(Long userId);
	
	Set<String> findPermissionByUserId(Long userId);
	
	List<UMenu> getAllMenuList() throws Exception;
	
	/**
	 * 查询用户角色列表
	 * @param findContent
	 * @return
	 */
	List<UserRoleAllocationBo> findUserAndRole(String findContent);
	
	/**
	 * 根据用户查询角色，及该用户可分配的角色
	 * @param userId
	 * @return
	 */
	List<URoleBo> selectRoleByUserId(Long userId);
	
	/**
	 * 查询角色权限列表
	 * @param findContent
	 * @return
	 */
	List<RolePermissionAllocationBo> findRoleAndPermission(String findContent);
	
	/**
	 * 根据角色查询权限，及该角色可分配的权限
	 * @param roleId
	 * @return
	 */
	List<UPermissionBo> selectPermissionByRoleId(Long roleId);
}