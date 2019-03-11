package cn.itcast.ssm.po;

import java.io.Serializable;

import cn.itcast.ssm.po.UPermission;
import cn.itcast.ssm.utils.StringUtils;


/**
 * 
 * 权限选择
 * @author zhou-baicheng
 *
 */
public class UPermissionBo extends UPermission implements Serializable {
	private static final long serialVersionUID = 1L;
		
	/**
	 * role Id
	 */
	private String roleId;


	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
}
