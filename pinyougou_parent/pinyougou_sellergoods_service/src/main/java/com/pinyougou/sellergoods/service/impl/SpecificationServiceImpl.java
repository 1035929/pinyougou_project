package com.pinyougou.sellergoods.service.impl;
import java.util.List;
import java.util.Map;

import com.pinyougou.entity.PageResult;
import com.pinyougou.mapper.TbSpecificationOptionMapper;
import com.pinyougou.pojo.TbSpecificationOption;
import com.pinyougou.pojo.TbSpecificationOptionExample;
import com.pinyougou.pojogroup.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbSpecificationMapper;
import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.pojo.TbSpecificationExample;
import com.pinyougou.pojo.TbSpecificationExample.Criteria;
import com.pinyougou.sellergoods.service.SpecificationService;
import org.springframework.transaction.annotation.Transactional;


/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
@Transactional
public class SpecificationServiceImpl implements SpecificationService {

	@Autowired
	private TbSpecificationMapper specificationMapper;

	@Autowired
	private TbSpecificationOptionMapper tbSpecificationOptionMapper;
	/**
	 * 查询全部
	 */
	@Override
	public List<TbSpecification> findAll() {
		return specificationMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbSpecification> page=(Page<TbSpecification>) specificationMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(Specification specification) {
		//新增规格名称
		specificationMapper.insert(specification.getTbSpecification());
		this.insertOption(specification);
	}

	public void insertOption(Specification specification){
		//遍历新增规格选项
		List<TbSpecificationOption> tbSpecificationOptions = specification.getTbSpecificationOption();
		for (TbSpecificationOption tbSpecificationOption : tbSpecificationOptions) {
			//将刚规格名称的Id给规格选项新增(规格选项新增需要规格名称Id：spec_id)
			tbSpecificationOption.setSpecId(specification.getTbSpecification().getId());
			tbSpecificationOptionMapper.insert(tbSpecificationOption);
		}
	}
	
	/**
	 * 修改
	 */
	@Override
	public void update(Specification specification){
		//修改规格名称
		TbSpecification tbSpecification = specification.getTbSpecification();
		specificationMapper.updateByPrimaryKey(tbSpecification);
		//根据规格名称id删除规格选项
		TbSpecificationOptionExample example=new TbSpecificationOptionExample();
		example.createCriteria().andSpecIdEqualTo(tbSpecification.getId());
		tbSpecificationOptionMapper.deleteByExample(example);
		//插入传入过来的规格选项
		this.insertOption(specification);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public Specification findOne(Long id){
		TbSpecification tbSpecification = specificationMapper.selectByPrimaryKey(id);
		TbSpecificationOptionExample example= new TbSpecificationOptionExample();
		example.createCriteria().andSpecIdEqualTo(id);
		List<TbSpecificationOption> optionList = tbSpecificationOptionMapper.selectByExample(example);
		Specification specification = new Specification();
		specification.setTbSpecification(tbSpecification);
		specification.setTbSpecificationOption(optionList);
		return specification;
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			specificationMapper.deleteByPrimaryKey(id);
			TbSpecificationOptionExample example=new TbSpecificationOptionExample();
			example.createCriteria().andSpecIdEqualTo(id);
			tbSpecificationOptionMapper.deleteByExample(example);
		}		
	}
	
	
	@Override
	public PageResult findPage(TbSpecification specification, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbSpecificationExample example=new TbSpecificationExample();
		Criteria criteria = example.createCriteria();
		
		if(specification!=null){			
						if(specification.getSpecName()!=null && specification.getSpecName().length()>0){
				criteria.andSpecNameLike("%"+specification.getSpecName()+"%");
			}
	
		}

		Page<TbSpecification> page= (Page<TbSpecification>)specificationMapper.selectByExample(example);		
		return new PageResult(page.getTotal(), page.getResult());
	}


	//关联规格
	@Override
	public List<Map> findSpecList() {
		return specificationMapper.findSpecList();
	}

}
