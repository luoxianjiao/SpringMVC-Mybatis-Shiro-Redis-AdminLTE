package cn.itcast.ssm.service.security;

import java.util.List;

import cn.itcast.ssm.po.UMenu;

public interface MenuService {

	List<UMenu> queryUserMenus(Long userId) throws Exception;
	
}
