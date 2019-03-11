package cn.itcast.ssm.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.itcast.ssm.mapper.ItemsMapper;
import cn.itcast.ssm.mapper.ItemsMapperCustom;
import cn.itcast.ssm.po.Items;
import cn.itcast.ssm.po.ItemsCustom;
import cn.itcast.ssm.po.ItemsQueryVo;
import cn.itcast.ssm.service.ItemsService;

@Service
public class ItemsServiceImpl implements ItemsService{

	@Autowired
	private ItemsMapperCustom itemsMapperCustom;
	@Autowired
	private ItemsMapper itemsMapper;
	@Override
	public List<ItemsCustom> findItemsList(ItemsQueryVo itemsQueryVo)
			throws Exception {				
		return itemsMapperCustom.findItemsList(itemsQueryVo);
	}

	@Override
	public ItemsCustom findItemsById(Integer id) throws Exception {
		Items items = itemsMapper.selectByPrimaryKey(id);
		ItemsCustom itemsCustom = new ItemsCustom();
		//复制属性到ItemsCustom
		BeanUtils.copyProperties(items, itemsCustom);//左边的object同名属性复制“给”右边object同名属性		
		return itemsCustom;
	}

	@Override
	public ItemsCustom updateItems(Integer id, ItemsCustom itemsCustom)
			throws Exception {
		itemsCustom.setId(id);
		itemsMapper.updateByPrimaryKeyWithBLOBs(itemsCustom);
		return itemsCustom;
	}

	public ItemsCustom updateItems(ItemsCustom itemsCustom) throws Exception {
		itemsMapper.updateByPrimaryKeySelective(itemsCustom);
		return itemsCustom;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public ItemsCustom addItems(ItemsCustom itemsCustom) throws Exception {
		itemsMapper.insertSelective(itemsCustom);
//		String aa=null;
//		aa.toString();
		return itemsCustom;
	}

	@Override
	public void deleteItems(Integer id) throws Exception {
		itemsMapper.deleteByPrimaryKey(id);		
	}

	@Override
	public PageInfo<ItemsCustom> queryByPage(Integer pageNo, Integer pageSize)
			throws Exception {
		PageHelper.startPage(pageNo, pageSize);
		List<ItemsCustom> list = itemsMapperCustom.findItemsList(null);
		//用PageInfo对结果进行包装
		PageInfo<ItemsCustom> page = new PageInfo<>(list);
		System.out.println(page.getPageNum());
	    System.out.println(page.getPageSize());
	    System.out.println(page.getStartRow());
	    System.out.println(page.getEndRow());
	    System.out.println(page.getTotal());
	    System.out.println(page.getPages());	    
	    System.out.println(page.isHasPreviousPage());
	    System.out.println(page.isHasNextPage());
	    System.out.println(page.getNextPage());
	    System.out.println(page.getPrePage());
	    
	    
		return page;
	}
	
	
	
	
}
