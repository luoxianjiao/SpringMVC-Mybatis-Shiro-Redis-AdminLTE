package cn.itcast.ssm.service.security;

import java.util.List;
import java.util.Map;

import org.springframework.ui.ModelMap;

import com.github.pagehelper.PageInfo;

import cn.itcast.ssm.po.UUser;
import cn.itcast.ssm.po.URoleBo;
import cn.itcast.ssm.po.UserRoleAllocationBo;

public interface UUserService {

	int deleteByPrimaryKey(Long id);

	UUser insert(UUser record);

    UUser insertSelective(UUser record);

    UUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UUser record);

    int updateByPrimaryKey(UUser record);      


	Map<String, Object> deleteUserById(String ids);

	Map<String, Object> updateForbidUserById(Long id, Long status);	
	
	UUser findUserByEmail(String email);
	
	UUser md5Pswd(UUser user);
	
	String md5Pswd(String email ,String pswd);
	
	UUser login(String email ,String pswd);
	
	PageInfo<UUser> queryByPage(Integer pageNum, Integer pageSize);
}
