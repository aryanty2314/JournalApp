package net.company.unique.Service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTests
{

    @Autowired
   private RedisTemplate<String,Object> redisTemplate;

    @Test
    public void checking()
    {
     Object onj = redisTemplate.opsForValue().get("email");
        System.out.println(onj);
    }

}