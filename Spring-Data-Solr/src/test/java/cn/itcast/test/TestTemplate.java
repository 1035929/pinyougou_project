package cn.itcast.test;

import cn.itcast.pojo.TbItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-solr.xml")
public class TestTemplate {

    @Autowired
    private SolrTemplate solrTemplate;

    @Test
    public void add(){
        TbItem item = new TbItem();
        item.setId(1L);
        item.setTitle("华为Mate30");
        item.setCategory("手机");
        item.setBrand("华为");
        item.setSeller("华为旗舰店");
        item.setGoodsId(10L);
        item.setPrice(new BigDecimal(3999.01));
        solrTemplate.saveBean(item);
        solrTemplate.commit();
    }

    @Test
    public void findById(){
        TbItem item = solrTemplate.getById(1L, TbItem.class);
        System.out.println(item.getTitle());
    }

    @Test
    public void delete(){
        solrTemplate.deleteById("100");
        solrTemplate.commit();
    }

    @Test
    public void addArrayList(){
        List list = new ArrayList();
        for (int i = 0; i < 100; i++) {
            TbItem item = new TbItem();
            item.setId(i+1L);
            item.setTitle("华为Mate"+i);
            item.setCategory("手机");
            item.setBrand("华为"+i);
            item.setSeller("华为旗舰店");
            item.setGoodsId(10L);
            item.setPrice(new BigDecimal(3999.01+i));
            list.add(item);
        }
        solrTemplate.saveBeans(list);
        solrTemplate.commit();
    }

    @Test
    public void queryForPage(){
        Query query = new SimpleQuery("*:*");
        Criteria criteria = new Criteria("item_category").contains("手机");
        criteria=criteria.and("item_brand").contains("2");
        query.addCriteria(criteria);
        //query.setOffset(20);
        query.setRows(20);
        ScoredPage<TbItem> tbItems = solrTemplate.queryForPage(query, TbItem.class);
        for (TbItem tbItem : tbItems) {
            System.out.println(tbItem.getTitle()+"   "+tbItem.getBrand()+"   "+tbItem.getPrice());
        }
        System.out.println("总记录数:"+tbItems.getTotalElements());
        System.out.println("总页数:"+tbItems.getTotalPages());
    }

    @Test
    public void deleteAll(){
        Query query = new SimpleQuery("*:*");
        solrTemplate.delete(query);
        solrTemplate.commit();
    }
}
