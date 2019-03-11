package cn.itcast.ssm.service.security.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.itcast.ssm.mapper.UUserMapper;
import cn.itcast.ssm.mapper.UUserMapperCustom;
import cn.itcast.ssm.mapper.UUserRoleMapper;
import cn.itcast.ssm.po.UUser;
import cn.itcast.ssm.po.UUserExample;
import cn.itcast.ssm.po.UUserRole;
import cn.itcast.ssm.utils.LoggerUtils;
import cn.itcast.ssm.utils.MathUtil;

import cn.itcast.ssm.service.security.UUserService;
import cn.itcast.ssm.shiro.session.CustomSessionManager;
import cn.itcast.ssm.shiro.token.manager.TokenManager;
import cn.itcast.ssm.po.URoleBo;
import cn.itcast.ssm.po.UserRoleAllocationBo;


@Service
public class UUserServiceImpl implements UUserService {
	/***
	 * 用户手动操作Session
	 * */
	@Autowired
	CustomSessionManager customSessionManager;
	@Autowired
	UUserMapper userMapper;
	@Autowired
	UUserMapperCustom userMapperCustom;
	@Autowired
	UUserRoleMapper userRoleMapper;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return userMapper.deleteByPrimaryKey(id);
	}

	@Override
	public UUser insert(UUser entity) {
		userMapper.insert(entity);
		return entity;
	}

	@Override
	public UUser insertSelective(UUser entity) {
		userMapper.insertSelective(entity);
		return entity;
	}

	@Override
	public UUser selectByPrimaryKey(Long id) {
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(UUser entity) {
		return userMapper.updateByPrimaryKey(entity);
	}

	@Override
	public int updateByPrimaryKeySelective(UUser entity) {
		return userMapper.updateByPrimaryKeySelective(entity);
	}


	@Override
	public Map<String, Object> deleteUserById(String ids) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			int count=0;
			String[] idArray = new String[]{};
			if(StringUtils.contains(ids, ",")){
				idArray = ids.split(",");
			}else{
				idArray = new String[]{ids};
			}
			
			for (String id : idArray) {
				count+=this.deleteByPrimaryKey(new Long(id));
			}
			resultMap.put("status", 200);
			resultMap.put("count", count);
		} catch (Exception e) {
			LoggerUtils.fmtError(getClass(), e, "根据IDS删除用户出现错误，ids[%s]", ids);
			resultMap.put("status", 500);
			resultMap.put("message", "删除出现错误，请刷新后再试！");
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> updateForbidUserById(Long id, Long status) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			UUser user = selectByPrimaryKey(id);
			user.setStatus(status);
			updateByPrimaryKeySelective(user);
			
			//如果当前用户在线，需要标记并且踢出
			customSessionManager.forbidUserById(id,status);
			
			
			resultMap.put("status", 200);
		} catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", "操作失败，请刷新再试！");
			LoggerUtils.fmtError(getClass(), "禁止或者激活用户登录失败，id[%s],status[%s]", id,status);
		}
		return resultMap;
	}

	@Override
	public UUser findUserByEmail(String email) {
		UUserExample uUserExample = new UUserExample();
		uUserExample.createCriteria().andEmailEqualTo(email);
		List<UUser> uUserList = userMapper.selectByExample(uUserExample);
		if(uUserList!=null && uUserList.size() > 0) {
			return uUserList.get(0); 
		}
		return null;
	}
	
	/**
	 * 加工密码，和登录一致。
	 * @param user
	 * @return
	 */
	public UUser md5Pswd(UUser user){
		//密码为   email + '#' + pswd，然后MD5
		user.setPswd(md5Pswd(user.getEmail(),user.getPswd()));
		return user;
	}
	/**
	 * 字符串返回值
	 * @param email
	 * @param pswd
	 * @return
	 */
	public String md5Pswd(String email ,String pswd){
		pswd = String.format("%s#%s", email,pswd);
		pswd = MathUtil.getMD5(pswd);
		return pswd;
	}

	@Override
	public UUser login(String email, String pswd) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("email", email);
		map.put("pswd", pswd);
		UUser user = userMapperCustom.login(map);
		return user;
	}

	@Override
	public PageInfo<UUser> queryByPage(Integer pageNum, Integer pageSize) {		
		PageHelper.startPage(pageNum, pageSize);
		PageInfo<UUser> pageList = new PageInfo<>(userMapper.selectByExample(null));
		return pageList;
	}

	

	
	
	
}
