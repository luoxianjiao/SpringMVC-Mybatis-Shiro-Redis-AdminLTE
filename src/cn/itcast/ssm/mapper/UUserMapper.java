package cn.itcast.ssm.mapper;

import cn.itcast.ssm.po.UUser;
import cn.itcast.ssm.po.UUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UUserMapper {
    int countByExample(UUserExample example);

    int deleteByExample(UUserExample example);

    int deleteByPrimaryKey(Long userId);

    int insert(UUser record);

    int insertSelective(UUser record);

    List<UUser> selectByExample(UUserExample example);

    UUser selectByPrimaryKey(Long userId);

    int updateByExampleSelective(@Param("record") UUser record, @Param("example") UUserExample example);

    int updateByExample(@Param("record") UUser record, @Param("example") UUserExample example);

    int updateByPrimaryKeySelective(UUser record);

    int updateByPrimaryKey(UUser record);
}