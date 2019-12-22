package com.pinyougou.sellergoods.service.impl;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.pinyougou.entity.PageResult;
import com.pinyougou.mapper.*;
import com.pinyougou.pojo.*;
import com.pinyougou.pojogroup.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.pojo.TbGoodsExample.Criteria;
import com.pinyougou.sellergoods.service.GoodsService;
import org.springframework.transaction.annotation.Transactional;


/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
@Transactional
public class GoodsServiceImpl implements GoodsService {

	@Autowired
	private TbGoodsMapper goodsMapper;

	@Autowired
	private TbGoodsDescMapper tbGoodsDescMapper;

	@Autowired
	private TbItemMapper tbItemMapper;

	@Autowired
	private TbItemCatMapper tbItemCatMapper;

	@Autowired
	private TbBrandMapper tbBrandMapper;

	@Autowired
	private TbSellerMapper tbSellerMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbGoods> findAll() {
		return goodsMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbGoods> page=   (Page<TbGoods>) goodsMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(Goods goods) {
	/*	//添加商品列表
		TbGoods tbGoods = goods.getTbGoods();
		tbGoods.setAuditStatus("0");

		goodsMapper.insert(tbGoods);
		//添加商品详情表
		TbGoodsDesc tbGoodsDesc = goods.getTbGoodsDesc();
		//把商品表中的id插入到商品详情表中的goods_id字段中。
		tbGoodsDesc.setGoodsId(tbGoods.getId());
		tbGoodsDescMapper.insert(tbGoodsDesc);*/

		//添加字段值
		//审核状态
		goods.getTbGoods().setAuditStatus("0");//0未审核，1审核通过，2审核未通过
		//是否上架，默认是下架状态
		//goods.getTbGoods().setIsMarketable("0");//0下架 1上架
		goodsMapper.insert(goods.getTbGoods());
		//商品拓展表

		goods.getTbGoodsDesc().setGoodsId(goods.getTbGoods().getId());
		tbGoodsDescMapper.insert(goods.getTbGoodsDesc());

		saveItemList(goods);



	}

	private void setItemValue(Goods goods,TbItem tbItem){
		//图片
		List<Map> imageList = JSON.parseArray(goods.getTbGoodsDesc().getItemImages(), Map.class) ;
		if(imageList.size()>0){
			tbItem.setImage ( (String)imageList.get(0).get("url"));
		}
		//杂项
		tbItem.setGoodsId(goods.getTbGoods().getId());//商品SPU编号
		tbItem.setSellerId(goods.getTbGoods().getSellerId());//商家编号
		tbItem.setCategoryid(goods.getTbGoods().getCategory3Id());//商品分类编号（3级）
		tbItem.setCreateTime(new Date());//创建日期
		tbItem.setUpdateTime(new Date());//修改日期
		//3.品牌名称
		TbBrand brand =tbBrandMapper.selectByPrimaryKey(goods.getTbGoods().getBrandId());
		tbItem.setBrand(brand.getName());
		//4.分类名称
		TbItemCat itemCat =
				tbItemCatMapper.selectByPrimaryKey(goods.getTbGoods().getCategory3Id());
		tbItem.setCategory(itemCat.getName());
		//5.商家名称
		TbSeller seller =
				tbSellerMapper.selectByPrimaryKey(goods.getTbGoods().getSellerId());
		tbItem.setSeller(seller.getNickName());
	}



	/**
	 * 修改
	 */
	@Override
	public void update(Goods goods){
		//goods.getTbGoods().setAuditStatus("0");//设置未申请状态:如果是经过修改的商品，需要重新设置状态

		goodsMapper.updateByPrimaryKey(goods.getTbGoods());//保存商品列表
		tbGoodsDescMapper.updateByPrimaryKey(goods.getTbGoodsDesc());//保存商品扩展列表
		//删除原有的sku列表数据
		TbItemExample example = new TbItemExample();
		com.pinyougou.pojo.TbItemExample.Criteria criteria= example.createCriteria();
		criteria.andGoodsIdEqualTo(goods.getTbGoods().getId());
		tbItemMapper.deleteByExample(example);

		//添加新的sku列表数据
		saveItemList(goods);//插入商品sku列表数据
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public Goods findOne(Long id){
		Goods goods = new Goods();
		TbGoods tbGoods = goodsMapper.selectByPrimaryKey(id);
		goods.setTbGoods(tbGoods);
		TbGoodsDesc tbGoodsDesc = tbGoodsDescMapper.selectByPrimaryKey(id);
		goods.setTbGoodsDesc(tbGoodsDesc);
		TbItemExample example = new TbItemExample();
		com.pinyougou.pojo.TbItemExample.Criteria criteria=example.createCriteria();
		criteria.andGoodsIdEqualTo(id);
		List<TbItem> itemList = tbItemMapper.selectByExample(example);
		goods.setItemList(itemList);
		return goods;
	}


	private void saveItemList(Goods goods){
		/*//添加item表
		List<TbItem> itemsList = goods.getItemList();*/
		if("1".equals(goods.getTbGoods().getIsEnableSpec())){ //启用规格
			for (TbItem tbItem : goods.getItemList()) {
				//标题
				String title=goods.getTbGoods().getGoodsName();
				//获取规格选项
				Map<String,Object> specMap= JSON.parseObject(tbItem.getSpec());//获取item表中的规格json串
				for (String  key : specMap.keySet()) {
					title+=" "+specMap.get(key);
				}
				tbItem.setTitle(title);
				setItemValue(goods,tbItem);
				tbItemMapper.insert(tbItem);
			}
		}else{//不启用规格
			TbItem tbItem=new TbItem();
			tbItem.setTitle(goods.getTbGoods().getGoodsName());
			tbItem.setPrice( goods.getTbGoods().getPrice() );//价格
			tbItem.setStatus("1");//状态
			tbItem.setIsDefault("1");//是否默认
			tbItem.setNum(99999);//库存数量
			tbItem.setSpec("{}");
			setItemValue(goods,tbItem);
			tbItemMapper.insert(tbItem);
		}
	}


	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			TbGoods tbGoods = goodsMapper.selectByPrimaryKey(id);
			tbGoods.setIsDelete("1");
			goodsMapper.updateByPrimaryKey(tbGoods);
		}		
	}
	
	
		@Override
	public PageResult findPage(TbGoods goods, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbGoodsExample example=new TbGoodsExample();
		Criteria criteria = example.createCriteria();
		criteria.andIsDeleteIsNull();
		if(goods!=null){			
						if(goods.getSellerId()!=null && goods.getSellerId().length()>0){
//				criteria.andSellerIdLike("%"+goods.getSellerId()+"%");
							criteria.andSellerIdEqualTo(goods.getSellerId());
			}
			if(goods.getGoodsName()!=null && goods.getGoodsName().length()>0){
				criteria.andGoodsNameLike("%"+goods.getGoodsName()+"%");
			}
			if(goods.getAuditStatus()!=null && goods.getAuditStatus().length()>0){
				criteria.andAuditStatusLike("%"+goods.getAuditStatus()+"%");
			}
			if(goods.getIsMarketable()!=null && goods.getIsMarketable().length()>0){
				criteria.andIsMarketableLike("%"+goods.getIsMarketable()+"%");
			}
			if(goods.getCaption()!=null && goods.getCaption().length()>0){
				criteria.andCaptionLike("%"+goods.getCaption()+"%");
			}
			if(goods.getSmallPic()!=null && goods.getSmallPic().length()>0){
				criteria.andSmallPicLike("%"+goods.getSmallPic()+"%");
			}
			if(goods.getIsEnableSpec()!=null && goods.getIsEnableSpec().length()>0){
				criteria.andIsEnableSpecLike("%"+goods.getIsEnableSpec()+"%");
			}
			if(goods.getIsDelete()!=null && goods.getIsDelete().length()>0){
				criteria.andIsDeleteLike("%"+goods.getIsDelete()+"%");
			}
	
		}
		
		Page<TbGoods> page= (Page<TbGoods>)goodsMapper.selectByExample(example);		
		return new PageResult(page.getTotal(), page.getResult());
	}

	@Override
	public void updateStatus(Long[] ids, String status) {
		for (Long id : ids) {
			TbGoods tbGoods = goodsMapper.selectByPrimaryKey(id);
			tbGoods.setAuditStatus(status);
			goodsMapper.updateByPrimaryKey(tbGoods);
		}
	}

	/**
	 * 根据spu的id集合查询sku列表
	 * @param goodsIds
	 * @param status
	 * @return
	 */
	@Override
	public List<TbItem> findItemListByGoodsIdListAndStatus(Long[] goodsIds, String status) {
		TbItemExample example = new TbItemExample();
		TbItemExample.Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo(status);//已审核
		criteria.andGoodsIdIn(Arrays.asList(goodsIds));//指定条件:spuid集合
		return tbItemMapper.selectByExample(example);
	}

}
