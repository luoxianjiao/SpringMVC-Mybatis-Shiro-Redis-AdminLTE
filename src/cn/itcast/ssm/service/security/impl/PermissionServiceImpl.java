package cn.itcast.ssm.service.security.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.itcast.ssm.mapper.UPermissionMapper;
import cn.itcast.ssm.mapper.URolePermissionMapper;
import cn.itcast.ssm.mapper.UUserMapper;
import cn.itcast.ssm.mapper.UUserMapperCustom;
import cn.itcast.ssm.mapper.UUserRoleMapper;
import cn.itcast.ssm.po.RolePermissionAllocationBo;
import cn.itcast.ssm.po.UPermission;
import cn.itcast.ssm.po.UPermissionExample;
import cn.itcast.ssm.po.URolePermission;
import cn.itcast.ssm.po.URolePermissionExample;
import cn.itcast.ssm.service.security.PermissionService;
import cn.itcast.ssm.utils.LoggerUtils;
import cn.itcast.ssm.shiro.token.manager.TokenManager;
import cn.itcast.ssm.po.UPermissionBo;

@Service
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	UPermissionMapper permissionMapper;
	@Autowired
	UUserMapper userMapper;
	@Autowired
	URolePermissionMapper rolePermissionMapper;
	@Autowired
	UUserRoleMapper userRoleMapper;
	@Autowired
	UUserMapperCustom userMapperCustom;
	private static Logger logger = Logger.getLogger(PermissionServiceImpl.class);
	@Override
	public int deleteByPrimaryKey(Long id) {
		return permissionMapper.deleteByPrimaryKey(id);
	}

	@Override
	public UPermission insert(UPermission record) {
		permissionMapper.insert(record);
		return record;
	}
	
	@Override
	public int insertSelective(UPermission record) {		
		return permissionMapper.insertSelective(record);
	}

	@Override
	public UPermission selectByPrimaryKey(Long id) {
		return permissionMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(UPermission record) {
		return permissionMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(UPermission record) {
		return permissionMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public Set<String> findPermissionByUserId(Long userId) {		
		return userMapperCustom.findPermissionByUserId(userId);
	}

	@Override
	public PageInfo<UPermission> queryByPage(String findContent,
			Integer pageNum, Integer pageSize) throws Exception {
		PageHelper.startPage(pageNum, pageSize);
		UPermissionExample uPermissionExample = new UPermissionExample();
		if(StringUtils.isNotBlank(findContent)) {			
			uPermissionExample.or().andDescriptionLike("%"+findContent+"%");
			uPermissionExample.or().andPermissionLike("%"+findContent+"%");
		}
		List<UPermission> list = permissionMapper.selectByExample(uPermissionExample);
		PageInfo<UPermission> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	@Override
	public void deletePermissionByIds(String ids) throws Exception {
		String[] idArray = new String[]{};
		if(StringUtils.contains(ids, ",")){
			idArray = ids.split(",");
		}else{
			idArray = new String[]{ids};
		}		
		for (String idx : idArray) {
			Long id = new Long(idx);		
			this.deleteByPrimaryKey(id);			
		}
		
	}

	@Override
	public List<UPermissionBo> selectPermissionByRoleId(Long roleId) {		
		return userMapperCustom.selectPermissionByRoleId(roleId);
	}

	@Override
	public PageInfo<RolePermissionAllocationBo> queryRoleAndPermissionByPage(
			String findContent, Integer pageNum, Integer pageSize)
			throws Exception {
		PageHelper.startPage(pageNum, pageSize);
		List<RolePermissionAllocationBo> list = userMapperCustom.findRoleAndPermission(findContent);
		PageInfo<RolePermissionAllocationBo> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	@Transactional
	@Override
	public void addPermissionToRole(Long roleId, String permissionIds)
			throws Exception {
		URolePermissionExample rolePermissionExample = new URolePermissionExample();
		rolePermissionExample.or().andRoleIdEqualTo(roleId);
		//先删除该角色原有权限 
		rolePermissionMapper.deleteByExample(rolePermissionExample);
		if(StringUtils.isNotBlank(permissionIds)){
			String[] idArray = null;						
			if(StringUtils.contains(permissionIds, ",")){
				idArray = permissionIds.split(",");
			}else{
				idArray = new String[]{permissionIds};
			}
			//添加新的。
			for (String pid : idArray) {
				//这里严谨点可以判断，也可以不判断
				if(StringUtils.isNotBlank(pid)){
					URolePermission rolePermission = new URolePermission();
					rolePermission.setRoleId(roleId);
					rolePermission.setPermissionId(Long.valueOf(pid));
					rolePermission.setCreate_time(new Date());
					rolePermissionMapper.insertSelective(rolePermission);
				}
			}
		}
		
	}

	
	
	
	
	

	
	
	

	

	
	

	

	
}
