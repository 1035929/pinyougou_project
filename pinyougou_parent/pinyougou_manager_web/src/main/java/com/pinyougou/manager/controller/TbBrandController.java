package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.entity.Result;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.sellergoods.service.TbBrandService;
import com.pinyougou.entity.PageResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/brand")
public class TbBrandController {
    @Reference
    private TbBrandService tbBrandService;

    @RequestMapping("/findAll")
    public List<TbBrand> findAll(){
        return tbBrandService.findAll();

    }

    /**
     * 分页查询品牌表
     * @param page 当前页码
     * @param size 每页显示条数
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult findByPage(int page,int size){

        PageResult pageResult = tbBrandService.findByPage(page,size);
        return pageResult;
    }

    @RequestMapping("/insertBrand")
    public Result save(@RequestBody TbBrand tbBrand){
        try {
            tbBrandService.insertBrand(tbBrand);
            return new Result(true,"新增成功");
        } catch (Exception e) {
            return new Result(false,"新增失败");

        }
    }

    //单一查询
    @RequestMapping("/findOne")
    public TbBrand findById(long id){
        return tbBrandService.findById(id);
    }

    //修改功能
    @RequestMapping("/updateBrand")
    public Result updateBrand(@RequestBody TbBrand tbBrand){
        try {
            tbBrandService.updateBrand(tbBrand);
            return new Result(true,"修改成功");
        } catch (Exception e) {
            return new Result(false,"修改失败");
        }
    }

    //删除功能
    @RequestMapping("/deleteBrand")
    public Result deleteBrand(Long[] ids){
        try {
            tbBrandService.deleteBrand(ids);
            return new Result(true,"删除成功");
        } catch (Exception e) {
            return new Result(false,"删除失败");
        }
    }

    //条件查询
    @RequestMapping("/search")
    public PageResult search(int page,int size,@RequestBody TbBrand tbBrand){
        return tbBrandService.search(page,size,tbBrand);

    }

    @RequestMapping("/selectBrandList")
    public List<Map> selectBrandList(){
        return tbBrandService.selectBrandList();
    }
}
