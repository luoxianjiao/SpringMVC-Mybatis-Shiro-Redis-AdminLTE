package cn.itcast.ssm.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;

import test.HelloWorld;
import test.SpringContextHolder;

import cn.itcast.ssm.po.ItemsCustom;
import cn.itcast.ssm.po.ItemsQueryVo;
import cn.itcast.ssm.service.ItemsService;

/**
 * 使用注解开发
 * @author xianjiao.luo
 *
 */
@Controller
@RequestMapping("/items")
public class ItemsController implements ApplicationContextAware{
	
	
	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		HelloWorld helloWorld = (HelloWorld) arg0.getBean("helloWorld");
		System.out.println(helloWorld.getMessage());
	}


	private static Logger logger = Logger.getLogger(ItemsController.class);
	private static int order = 0;
	@Autowired
	private ItemsService itemsService;
	
	@Resource
	private ApplicationContext applicationContext;
	
	//商品模糊查询
	@RequestMapping("/queryItems")
	public ModelAndView queryItems(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*1. 配合注解方式 获取ApplicationContext*/
//		HelloWorld helloWorld = (HelloWorld) applicationContext.getBean("helloWorld");
//		System.out.println(helloWorld.getMessage());
		/*2. 实现ApplicationContextAware接口 来获取ApplicationContext*/
//		SpringContextHolder springContextHolder = new SpringContextHolder();
//		ApplicationContext applicationContext = springContextHolder.applicationContext;
		/*3. 使用工具类WebApplicationContextUtils*/
//		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		/*4. 从当前线程绑定获取 （Spring boot不支持）*/
//		ApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
//		HelloWorld helloWorld =(HelloWorld) ctx.getBean("helloWorld");
//		System.out.println(helloWorld.getMessage());
		
		//调用service查询数据库 得到商品列表
		List<ItemsCustom> itemsCustomList = itemsService.findItemsList(null);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("itemsList", itemsCustomList);
		modelAndView.setViewName("items/itemsList");
		
//		throw new CustomException("有异常啊哥哥");
		Cookie[] arr  = request.getCookies();
		if(arr !=null) {
			for (int i = 0; i < arr.length; i++) {
				Cookie _cookie = arr[i];
				System.out.println(_cookie.getName()+":"+_cookie.getValue());
			}
		}		
		Cookie c = new Cookie("name", "Tom");
		Cookie c1 = new Cookie("11", "33");
		response.addCookie(c);
		
		//测试分页
		PageInfo<ItemsCustom> page = itemsService.queryByPage(1, 10);
		modelAndView.addObject("itemsList", page.getList());
		
		
		return modelAndView;		
	}
	
	@RequestMapping(value="/editItems",method=RequestMethod.GET)
	public ModelAndView editItems(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		int itemsId = Integer.parseInt(request.getParameter("id"));
		ItemsCustom itemsCustom = itemsService.findItemsById(itemsId);
		modelAndView.addObject("itemsCustom", itemsCustom);
		modelAndView.setViewName("items/editItems1");
		return modelAndView;		
	}
	
	/*
	 * 参数绑定时，需要  视图name“属性值”与 参数名 须一致
	 * 普通pojo绑定时，需要 视图name“属性值”与pojo对象的“属性名称” 一致	 
	 */
	@RequestMapping(value="/editItemsSubmit", method=RequestMethod.POST)
	public ModelAndView editItemsSubmit(HttpServletRequest request,Integer id, ItemsCustom itemsCustom, HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
//		int id = NumberUtils.toInt(request.getParameter("id"));
//		String name = request.getParameter("name");
//		Float price = Float.parseFloat(request.getParameter("price"));
//		Date createtime = DateUtils.parseDate(request.getParameter("createtime"), "yyyy-MM-dd HH:mm:ss");		
//		String detail = request.getParameter("detail");
//		ItemsCustom itemsCustom = new ItemsCustom();
//		itemsCustom.setName(name);
//		itemsCustom.setDetail(detail);
//		itemsCustom.setPrice(price);
//		itemsCustom.setCreatetime(createtime);
		itemsService.updateItems(id, itemsCustom);
		modelAndView.setViewName("redirect:queryItems.action");//重定向 ，url变化				
		return modelAndView;
	}
	
	/*
	 * 包装类型pojo参数绑定时
	 */
	@RequestMapping(value="/editItemsSubmit_1")
	public ModelAndView editItemsSubmit(HttpServletRequest request, ItemsQueryVo itemsQueryVo, MultipartFile items_pic) throws Exception {
		ModelAndView mv = new ModelAndView();
		String originalFilename = items_pic.getOriginalFilename();
		String savePath = "D:/stuff/";
		String newFilename = getDateCodedr() + "." + FilenameUtils.getExtension(originalFilename);
		items_pic.transferTo(new File(savePath + newFilename));
		itemsQueryVo.getItemsCustom().setPic(newFilename);
		System.out.println(itemsQueryVo.getItemsCustom());
		itemsService.updateItems(itemsQueryVo.getItemsCustom());
		mv.setViewName("redirect:queryItems.action");//重定向
		
		return mv;
	}
	
	/*
	 * 单个删除
	 */
	@RequestMapping(value="/deleteItems")
	public String deleteItems(HttpServletRequest request) throws Exception {
		Integer id = Integer.valueOf(request.getParameter("id"));
		itemsService.deleteItems(id);						
		return "redirect:queryItems.action";
	}
	
	/*
	 * 数组参数绑定，批量删除时
	 */
	@RequestMapping(value="/deleteItemsBatch")
	public String deleteItems(Integer[] items_id) throws Exception {
		System.out.println(items_id);
//		itemsService.deleteItems(id);						
		return "redirect:queryItems.action";
	}
	
	/**
	 * 新增页面
	 * @param request
	 * @param itemsCustom
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/addItems")
	public ModelAndView addItems(HttpServletRequest request, ItemsCustom itemsCustom) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("items/addItems");
		return mv;
	}
	
	/**
	 * 提交新增商品
	 * @param request
	 * @param itemsCustom
	 * @throws Exception
	 */
	@RequestMapping(value="/addItemsSubmit",method=RequestMethod.POST)
	public String addItemsSubmit(HttpServletRequest request, ItemsCustom itemsCustom, MultipartFile items_pic) throws Exception {
		
		String originalFilename = items_pic.getOriginalFilename();
		if(!StringUtils.isBlank(originalFilename)) {
			String pic_path = "D:/stuff/";
			String newFilename = getDateCodedr() + "." + FilenameUtils.getExtension(originalFilename);
			File newFile = new File(pic_path + newFilename);
			//将内存中的数据存入磁盘
			items_pic.transferTo(newFile);
			itemsCustom.setPic(newFilename);			
		}
		itemsService.addItems(itemsCustom);
		return "redirect:queryItems.action";
	}
	
	@RequestMapping(value="/requestJson",method=RequestMethod.POST)
	public @ResponseBody ItemsCustom requestJson(@RequestBody ItemsCustom itemsCustom) throws Exception {			
		return itemsCustom;
				
	} 
	
	@RequestMapping(value="/requestJson_1",method=RequestMethod.POST)
	public @ResponseBody ItemsCustom requestJson_1(HttpServletRequest request) throws Exception {
		ItemsCustom itemsCustom = new ItemsCustom();
		String name = request.getParameter("name");
		float price = Float.valueOf(request.getParameter("price"));
		String detail = request.getParameter("detail");
		itemsCustom.setName(name);
		itemsCustom.setPrice(price);
		itemsCustom.setDetail(detail);
		return itemsCustom;				
	} 
	
	@RequestMapping(value="/requestJson_2",method=RequestMethod.POST)
	public @ResponseBody ItemsCustom requestJson_3(ItemsCustom itemsCustom) throws Exception {	
//		System.setProperty("java.awt.headless", "true");
		return itemsCustom;
				
	} 
	/**
  	 * 
  	  * @Title: getDateCodedr
  	  * @Description: 取当前时间为编号
  	  * @param @return    设定文件
  	  * @return String    返回类型
  	  * @throws
  	 */
  	public static String getDateCodedr(){  		
  		
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddhhmmss");
		String codeNo = sdf.format(new Date());
		codeNo=codeNo+order;
		if(order>8){
			order=0;
		}else{
			order++;
		}
		return codeNo;
	}
  	
  	
  	@Test
  	public void queryByPageTest() throws Exception {
  		
  		itemsService.queryByPage(1, 3);
  		
  	}
  	
  	
  	
  	
  	
  	
  	
  	
  	public static void main(String[] args) {
		HashMap<String, Object> map = new HashMap<>();
		HashMap<String, Object> map1 = new HashMap<>(16, 0.75f);//负载因子 loadFactor 衡量的是一个散列表的空间的使用程度，负载因子越大表示散列表的装填程度越高
		map.put("1", "xj");
		map.put("2", "lm");
		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry entry = (Entry) it.next();
			Object key = entry.getKey();
			Object value = entry.getValue();
			System.out.println(key);
			System.out.println(value);
			
		}
		System.out.println();
		Iterator it1 = map.keySet().iterator();
		while (it1.hasNext()) {
			String key = (String) it1.next();
			String value = (String) map.get(key);
			System.out.println(key);
			System.out.println(value);
			
		}
	}
}
