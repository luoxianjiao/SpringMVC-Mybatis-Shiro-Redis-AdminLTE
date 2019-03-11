package cn.itcast.ssm.po;

public class URole {
    private Long roleId;

    private String name;

    private String type;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

	@Override
	public String toString() {
		return "URole [roleId=" + roleId + ", name=" + name + ", type=" + type
				+ "]";
	}
    
    
}