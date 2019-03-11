package cn.itcast.ssm.mapper;

import cn.itcast.ssm.po.URolePermission;
import cn.itcast.ssm.po.URolePermissionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface URolePermissionMapper {
    int countByExample(URolePermissionExample example);

    int deleteByExample(URolePermissionExample example);

    int insert(URolePermission record);

    int insertSelective(URolePermission record);

    List<URolePermission> selectByExample(URolePermissionExample example);

    int updateByExampleSelective(@Param("record") URolePermission record, @Param("example") URolePermissionExample example);

    int updateByExample(@Param("record") URolePermission record, @Param("example") URolePermissionExample example);
}