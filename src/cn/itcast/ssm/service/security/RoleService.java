package cn.itcast.ssm.service.security;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.pagehelper.PageInfo;

import cn.itcast.ssm.po.URole;
import cn.itcast.ssm.po.RolePermissionAllocationBo;
import cn.itcast.ssm.po.URoleBo;
import cn.itcast.ssm.po.URoleExample;
import cn.itcast.ssm.po.UserRoleAllocationBo;

public interface RoleService {

	int deleteByPrimaryKey(Long id) throws Exception;

    int insert(URole record) throws Exception;

    int insertSelective(URole record) throws Exception;

    URole selectByPrimaryKey(Long id) throws Exception;

    int updateByPrimaryKeySelective(URole record) throws Exception;

    int updateByPrimaryKey(URole record) throws Exception;

    void deleteRoleByIds(String ids) throws Exception;
	
	List<URole> selectByExample(URoleExample example) throws Exception;
	
	PageInfo<URole> queryByPage(String findContent, Integer pageNo, Integer pageSize) throws Exception;
	
	//根据用户ID查询角色（role），放入到Authorization里。
	Set<String> findRoleByUserId(Long userId);
	
	/**
	 * 查询用户角色分配列表
	 * @param findContent
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	PageInfo<UserRoleAllocationBo> queryUserRoleAllocationByPage(String findContent,Integer pageNo, Integer pageSize) throws Exception;
	
	/**
	 * 查询所有角色，并关联上根据userId（分配角色使用）
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	List<URoleBo> selectRoleByUserId(Long userId) throws Exception;
	
	/**
	 * 为用户分配角色
	 * @param userId
	 * @param roleIds
	 * @throws Exception
	 */
	void addRoleToUser(Long userId, String roleIds) throws Exception;
	
	
	
}
