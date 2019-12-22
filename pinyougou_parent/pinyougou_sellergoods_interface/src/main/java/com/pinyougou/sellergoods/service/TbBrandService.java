package com.pinyougou.sellergoods.service;



import com.pinyougou.entity.PageResult;
import com.pinyougou.pojo.TbBrand;


import java.util.List;
import java.util.Map;

/*
品牌接口
 */
public interface TbBrandService {

    //全查
    public List<TbBrand> findAll();
    //分页
    public PageResult findByPage(int page, int size);
    //新增
    public void insertBrand(TbBrand tbBrand);

    //单一查询
    public TbBrand findById(long id);
    //修改功能
    public void updateBrand(TbBrand tbBrand);

    //删除功能
    public void deleteBrand(Long[] ids);

    //条件查询
    public PageResult search(int page, int size, TbBrand tbBrand);

    //select2
    public List<Map> selectBrandList();
}
