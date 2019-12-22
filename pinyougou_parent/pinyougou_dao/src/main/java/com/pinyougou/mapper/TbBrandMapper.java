package com.pinyougou.mapper;

import com.pinyougou.pojo.TbBrand;
import com.pinyougou.pojo.TbBrandExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface TbBrandMapper {
    //根据自定义查询总记录数
    int countByExample(TbBrandExample example);
    //根据自定义条件删除
    int deleteByExample(TbBrandExample example);
    //根据主键删除
    int deleteByPrimaryKey(Long id);
    //新增
    int insert(TbBrand record);
    //选择性新增
    int insertSelective(TbBrand record);
    //根据自定义查询，返回值list类型
    List<TbBrand> selectByExample(TbBrandExample example);
    //单一查询
    TbBrand selectByPrimaryKey(Long id);
    //根据自定义条件选择性修改
    int updateByExampleSelective(@Param("record") TbBrand record, @Param("example") TbBrandExample example);
    //根据自定义条件修改
    int updateByExample(@Param("record") TbBrand record, @Param("example") TbBrandExample example);
    //根据主键择性修改
    int updateByPrimaryKeySelective(TbBrand record);
    //主键修改
    int updateByPrimaryKey(TbBrand record);
    //关联品牌
    List<Map> selectBrandList();
}