package com.pinyougou.solrutil;

import com.alibaba.fastjson.JSON;
import com.pinyougou.mapper.TbItemMapper;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojo.TbItemExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class SolrUtil {
    @Autowired
    private TbItemMapper itemMapper;

    @Autowired
    private SolrTemplate solrTemplate;

    public void importData(){
        TbItemExample example = new TbItemExample();
        TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo("1");
        List<TbItem> tbItems = itemMapper.selectByExample(example);
        System.out.println("----商品列表----");
        for (TbItem tbItem : tbItems) {
            System.out.println(tbItem.getId()+"  "+tbItem.getTitle()+"  "+tbItem.getPrice());
            Map specMap = JSON.parseObject(tbItem.getSpec(), Map.class);
            tbItem.setSpecMap(specMap);
        }
        solrTemplate.saveBeans(tbItems);
        solrTemplate.commit();
        System.out.println("----结束----");
    }
}
