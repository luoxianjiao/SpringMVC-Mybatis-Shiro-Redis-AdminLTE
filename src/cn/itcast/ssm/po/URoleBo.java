package cn.itcast.ssm.po;

import java.io.Serializable;

import cn.itcast.ssm.po.URole;
import cn.itcast.ssm.utils.StringUtils;


public class URoleBo extends URole implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 用户ID (用String， 考虑多个ID，现在只有一个ID)
	 */
	private String userId;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
