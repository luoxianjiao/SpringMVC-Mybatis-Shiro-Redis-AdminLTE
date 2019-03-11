package cn.itcast.ssm.service.security.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.itcast.ssm.mapper.URoleMapper;
import cn.itcast.ssm.mapper.URolePermissionMapper;
import cn.itcast.ssm.mapper.UUserMapper;
import cn.itcast.ssm.mapper.UUserMapperCustom;
import cn.itcast.ssm.mapper.UUserRoleMapper;
import cn.itcast.ssm.po.URole;
import cn.itcast.ssm.po.URoleBo;
import cn.itcast.ssm.po.URoleExample;
import cn.itcast.ssm.po.UUserRole;
import cn.itcast.ssm.po.UUserRoleExample;
import cn.itcast.ssm.po.UserRoleAllocationBo;
import cn.itcast.ssm.utils.LoggerUtils;
import cn.itcast.ssm.service.security.RoleService;
import cn.itcast.ssm.shiro.token.manager.TokenManager;



@Service
@SuppressWarnings("unchecked")
public class RoleServiceImpl implements RoleService {

	@Autowired
	URoleMapper roleMapper;
	@Autowired
	UUserMapper userMapper;
	@Autowired
	UUserMapperCustom userMapperCustom;
	@Autowired
	URolePermissionMapper rolePermissionMapper;
	@Autowired
	UUserRoleMapper userRoleMapper;
	private static Logger logger = Logger.getLogger(RoleServiceImpl.class);

	@Override
	public int deleteByPrimaryKey(Long id) {
		return roleMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(URole record) {
		return roleMapper.insert(record);
	}

	@Override
	public int insertSelective(URole record) {
		return roleMapper.insertSelective(record);
	}

	@Override
	public URole selectByPrimaryKey(Long id) {
		return roleMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(URole record) {
		return roleMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(URole record) {
		return roleMapper.updateByPrimaryKeySelective(record);
	}

	
	
	
	@Transactional
	@Override
	public void deleteRoleByIds(String ids) throws Exception {					
		String[] idArray = new String[]{};
		if(StringUtils.contains(ids, ",")){
			idArray = ids.split(",");
		}else{
			idArray = new String[]{ids};
		}		
		c:for (String idx : idArray) {
			Long id = new Long(idx);
			if(new Long(1).equals(id)){
				logger.warn("操作成功，But'系统管理员不能删除");				
				continue c;
			}else{
				this.deleteByPrimaryKey(id);
			}
		}			
	}

	@Override
	public List<URole> selectByExample(URoleExample example) throws Exception {		
		return roleMapper.selectByExample(example);
	}

	@Override
	public PageInfo<URole> queryByPage(String findContent, Integer pageNum,
			Integer pageSize) throws Exception {
		PageHelper.startPage(pageNum, pageSize);
		URoleExample roleExample = new URoleExample();
		if(StringUtils.isNotBlank(findContent)) {
			roleExample.or().andNameEqualTo(findContent);
			roleExample.or().andTypeEqualTo(findContent);
		}						
		List<URole> roleList = roleMapper.selectByExample(roleExample);
		PageInfo<URole> pageList = new PageInfo<>(roleList);
		return pageList;
	}

	@Override
	public Set<String> findRoleByUserId(Long userId) {
		return userMapperCustom.findRoleByUserId(userId);
	}

	@Override
	public PageInfo<UserRoleAllocationBo> queryUserRoleAllocationByPage(
			String findContent, Integer pageNo, Integer pageSize)
			throws Exception {
		PageHelper.startPage(pageNo, pageSize);
		List<UserRoleAllocationBo> list = userMapperCustom.findUserAndRole(findContent);
		PageInfo<UserRoleAllocationBo> pageList = new PageInfo<>(list);		
		return pageList;
	}

	@Override
	public List<URoleBo> selectRoleByUserId(Long userId) throws Exception {		
		return userMapperCustom.selectRoleByUserId(userId);
	}

	@Transactional
	@Override
	public void addRoleToUser(Long userId, String roleIds) throws Exception {
		UUserRoleExample userRoleExample = new UUserRoleExample();
		userRoleExample.or().andUserIdEqualTo(userId);
		//删除原有的角色
		userRoleMapper.deleteByExample(userRoleExample);
		//如果roleIds有值，那么就添加。没值就把这个用户（userId）所有角色取消。
		if(StringUtils.isNotBlank(roleIds)){
			String[] idArray = null;						
			if(StringUtils.contains(roleIds, ",")){
				idArray = roleIds.split(",");
			}else{
				idArray = new String[]{roleIds};
			}
			//分配新的角色
			for (String rid : idArray) {				
				if(StringUtils.isNotBlank(rid)){
					UUserRole userRole = new UUserRole();
					userRole.setUserId(userId);
					userRole.setRoleId(Long.valueOf(rid));					
					userRoleMapper.insertSelective(userRole);
				}
			}
		}		
		
	}
	
	
	
	

	
	/*public Map<String,Object> addRole(URole role) throw{
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();		
		try {
			int count = roleMapper.insertSelective(role);			
			resultMap.put("status", 0);
			resultMap.put("msg", "成功");
		} catch (Exception e) {
			resultMap.put("status", -1);
			resultMap.put("msg", "失败");
		}
		return resultMap;
	}*/
	
	
	
	
}
