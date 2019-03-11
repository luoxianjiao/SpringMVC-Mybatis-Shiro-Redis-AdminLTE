package cn.itcast.ssm.controller.app;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.ssm.service.ItemsService;


@Controller
@RequestMapping("/app")
public class AppAbstractController {
	
	private static Logger logger = Logger.getLogger(AppAbstractController.class);
	
	public ItemsService itemsService;
	
}
