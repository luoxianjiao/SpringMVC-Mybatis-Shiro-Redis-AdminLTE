package cn.itcast.ssm.mapper;

import cn.itcast.ssm.po.UMenu;
import cn.itcast.ssm.po.UMenuExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UMenuMapper {
    int countByExample(UMenuExample example);

    int deleteByExample(UMenuExample example);

    int deleteByPrimaryKey(Long menuId);

    int insert(UMenu record);

    int insertSelective(UMenu record);

    List<UMenu> selectByExample(UMenuExample example);

    UMenu selectByPrimaryKey(Long menuId);

    int updateByExampleSelective(@Param("record") UMenu record, @Param("example") UMenuExample example);

    int updateByExample(@Param("record") UMenu record, @Param("example") UMenuExample example);

    int updateByPrimaryKeySelective(UMenu record);

    int updateByPrimaryKey(UMenu record);
}