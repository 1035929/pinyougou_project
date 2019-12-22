package test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext-redis.xml")
public class TestList {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void setListValue1(){
        redisTemplate.boundListOps("namelist").rightPush("刘备");
        redisTemplate.boundListOps("namelist").rightPush("关羽");
        redisTemplate.boundListOps("namelist").rightPush("张飞");
    }

    @Test
    public void getListValue1(){
        List namelist = redisTemplate.boundListOps("namelist").range(0,10);
        System.out.println(namelist);
    }

    @Test
    public void setListValue2(){
        redisTemplate.boundListOps("namelist2").leftPush("刘备");
        redisTemplate.boundListOps("namelist2").leftPush("关羽");
        redisTemplate.boundListOps("namelist2").leftPush("张飞");
    }

    @Test
    public void getListValue2(){
        List namelist2 = redisTemplate.boundListOps("namelist2").range(0, 10);
        System.out.println(namelist2);
    }

    @Test
    public void removeValue(){
        redisTemplate.boundListOps("namelist").remove(0,"刘备");
    }

    @Test
    public void delete(){
        redisTemplate.delete("namelist");
    }
}
