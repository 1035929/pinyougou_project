package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.entity.PageResult;
import com.pinyougou.mapper.TbBrandMapper;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.pojo.TbBrandExample;
import com.pinyougou.sellergoods.service.TbBrandService;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Service//dubbo包下
public class TbBrandServiceImpl  implements TbBrandService{

    @Autowired//本地引用
    private TbBrandMapper tbBrandMapper;

    @Override
    public List<TbBrand> findAll() {
        TbBrandExample example=new TbBrandExample();
        return tbBrandMapper.selectByExample(example);
    }

    /**
     * 分页
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageResult findByPage(int page, int size) {
        PageHelper.startPage(page,size);
        Page<TbBrand> brands =(Page<TbBrand>)tbBrandMapper.selectByExample(null);
        return new PageResult(brands.getTotal(),brands.getResult());
    }

    /**
     * 新增
     * @param tbBrand
     */
    @Override
    public void insertBrand(TbBrand tbBrand) {

        tbBrandMapper.insert(tbBrand);
    }

    /**
     * 单一查询
     * @param id
     * @return
     */
    @Override
    public TbBrand findById(long id) {
        return tbBrandMapper.selectByPrimaryKey(id);
    }

    /**
     * 修改功能
     * @param tbBrand
     */
    @Override
    public void updateBrand(TbBrand tbBrand) {
        tbBrandMapper.updateByPrimaryKey(tbBrand);
    }

    @Override
    public void deleteBrand(Long[] ids) {
        for (Long id : ids) {
            tbBrandMapper.deleteByPrimaryKey(id);
        }
    }

    @Override
    public PageResult search(int page, int size, TbBrand tbBrand) {
        PageHelper.startPage(page, size);
        TbBrandExample example = new TbBrandExample();

        TbBrandExample.Criteria criteria = example.createCriteria();
        if (tbBrand.getName()!=null && !"".equals(tbBrand.getName())){
            criteria.andNameLike("%"+tbBrand.getName()+"%");
        }
        if (tbBrand.getFirstChar()!=null && !"".equals(tbBrand.getFirstChar()) ){
            criteria.andFirstCharEqualTo(tbBrand.getFirstChar());
        }

        Page<TbBrand> tbBrands = (Page<TbBrand>) tbBrandMapper.selectByExample(example);

        return new PageResult(tbBrands.getTotal(),tbBrands.getResult());
    }

    //select2
    @Override
    public List<Map> selectBrandList() {
        return tbBrandMapper.selectBrandList();
    }




}
