package cn.itcast.ssm.service;

import java.util.List;

import com.github.pagehelper.PageInfo;

import cn.itcast.ssm.po.Items;
import cn.itcast.ssm.po.ItemsCustom;
import cn.itcast.ssm.po.ItemsQueryVo;

public interface ItemsService {
	
	public  int a =1;
	/**
	 * 根据条件模糊查询商品列表
	 * @param itemsQueryVo
	 * @return
	 * @throws Exception
	 */
	public List<ItemsCustom> findItemsList(ItemsQueryVo itemsQueryVo) throws Exception;
	
	/**
	 * 根据id查询商品
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public ItemsCustom findItemsById(Integer id) throws Exception;
	
	/**
	 * 修改商品信息
	 * @param id
	 * @param itemsCustom
	 * @return
	 * @throws Exception
	 */
	public ItemsCustom updateItems(Integer id, ItemsCustom itemsCustom) throws Exception;
	
	/**
	 * 修改商品信息
	 * @param id
	 * @param itemsCustom
	 * @return
	 * @throws Exception
	 */
	public ItemsCustom updateItems(ItemsCustom itemsCustom) throws Exception;
	
	/**
	 * 新增商品信息
	 * @param itemsCustom
	 * @return
	 * @throws Exception
	 */
	public ItemsCustom addItems(ItemsCustom itemsCustom) throws Exception;
	
	/**
	 * 
	 * @param itemsCustom
	 * @return
	 * @throws Exception
	 */
	public void deleteItems(Integer id) throws Exception;
	
	
	public PageInfo<ItemsCustom> queryByPage(Integer pageNo,Integer pageSize) throws Exception;
}
