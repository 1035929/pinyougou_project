package test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext-redis.xml")
public class TestSet {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void setSetValue(){
        redisTemplate.boundSetOps("name").add("顾与南歌");
        redisTemplate.boundSetOps("name").add("念与北诗");
        redisTemplate.boundSetOps("name").add("一片孤城万仞山");
    }

    @Test
    public void getValue(){
        Set names = redisTemplate.boundSetOps("name").members();
        System.out.println(names);
    }

    @Test
    public void removeValue(){
        redisTemplate.boundSetOps("name").remove("一片孤城万仞山");
    }

    @Test
    public void deleteValue(){
        redisTemplate.delete("name");
    }
}
