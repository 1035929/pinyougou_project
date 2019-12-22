package test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/spring/applicationContext-redis.xml")
public class TestHash {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void setHash(){
        redisTemplate.boundHashOps("namehash").put("a","唐僧");
        redisTemplate.boundHashOps("namehash").put("b","悟空");
        redisTemplate.boundHashOps("namehash").put("c","八戒");
        redisTemplate.boundHashOps("namehash").put("d","沙僧");
    }

    @Test
    public void getHashKeys(){
        Set keys = redisTemplate.boundHashOps("namehash").keys();
        System.out.println(keys);
    }

    @Test
    public void getHashValue(){
        List namehash = redisTemplate.boundHashOps("namehash").values();
        System.out.println(namehash);
    }

    @Test
    public void getHashValueByKey(){
        String name = (String) redisTemplate.boundHashOps("namehash").get("b");
        System.out.println(name);
    }

    @Test
    public void deleteHash(){
        redisTemplate.boundHashOps("namehash").delete("a","d");
    }

    @Test
    public void delete(){
        redisTemplate.delete("namehash");
    }
}
