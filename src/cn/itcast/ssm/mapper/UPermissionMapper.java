package cn.itcast.ssm.mapper;

import cn.itcast.ssm.po.UPermission;
import cn.itcast.ssm.po.UPermissionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UPermissionMapper {
    int countByExample(UPermissionExample example);

    int deleteByExample(UPermissionExample example);

    int deleteByPrimaryKey(Long permissionId);

    int insert(UPermission record);

    int insertSelective(UPermission record);

    List<UPermission> selectByExample(UPermissionExample example);

    UPermission selectByPrimaryKey(Long permissionId);

    int updateByExampleSelective(@Param("record") UPermission record, @Param("example") UPermissionExample example);

    int updateByExample(@Param("record") UPermission record, @Param("example") UPermissionExample example);

    int updateByPrimaryKeySelective(UPermission record);

    int updateByPrimaryKey(UPermission record);
}