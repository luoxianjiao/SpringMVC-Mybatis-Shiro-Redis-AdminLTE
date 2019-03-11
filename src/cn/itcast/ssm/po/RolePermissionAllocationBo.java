package cn.itcast.ssm.po;

import java.io.Serializable;
/**
 * 权限分配 查询列表BO
 * 
 *
 */
public class RolePermissionAllocationBo implements Serializable {
	private static final long serialVersionUID = 1L;
	//角色ID
	private Long roleId;
	//角色type
	private String type;
	//角色Name
	private String name;
	//权限Name列转行，以,分割
	private String permissionNames;
	//权限Id列转行，以‘,’分割
	private String permissionIds;

	public Long getRoleId() {
		return roleId;
	}

	public void setId(Long roleId) {
		this.roleId = roleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPermissionNames() {
		return permissionNames;
	}

	public void setPermissionNames(String permissionNames) {
		this.permissionNames = permissionNames;
	}

	public String getPermissionIds() {
		return permissionIds;
	}

	public void setPermissionIds(String permissionIds) {
		this.permissionIds = permissionIds;
	}
	
	
}
