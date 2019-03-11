package cn.itcast.ssm.service.security.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.ssm.mapper.UUserMapperCustom;
import cn.itcast.ssm.po.UMenu;
import cn.itcast.ssm.service.security.MenuService;

@Service
public class MenuServiceImpl implements MenuService{

	@Autowired
	private UUserMapperCustom userMapperCustom;
	
	@Override
	public List<UMenu> queryUserMenus(Long userId) throws Exception {	
		List<UMenu> menuList = new ArrayList<>();
		Set<String> permissionSet = userMapperCustom.findPermissionByUserId(userId);		
		List<UMenu> allMenuList = userMapperCustom.getAllMenuList();
		for(UMenu uMenu : allMenuList) {
			String permission = uMenu.getPermission().trim();
			if(permissionSet.contains(permission)) {
				menuList.add(uMenu);
			} else {
				if(permission.equals("anon")) {
					menuList.add(uMenu);
				}
			}
		}
		return menuList;
	}

	
	
}
