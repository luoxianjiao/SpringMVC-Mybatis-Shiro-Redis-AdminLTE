package cn.itcast.ssm.mapper;

import cn.itcast.ssm.po.URole;
import cn.itcast.ssm.po.URoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface URoleMapper {
    int countByExample(URoleExample example);

    int deleteByExample(URoleExample example);

    int deleteByPrimaryKey(Long roleId);

    int insert(URole record);

    int insertSelective(URole record);

    List<URole> selectByExample(URoleExample example);

    URole selectByPrimaryKey(Long roleId);

    int updateByExampleSelective(@Param("record") URole record, @Param("example") URoleExample example);

    int updateByExample(@Param("record") URole record, @Param("example") URoleExample example);

    int updateByPrimaryKeySelective(URole record);

    int updateByPrimaryKey(URole record);
}