package net.company.unique.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class redisService
{

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ObjectMapper objectMapper; // ✅ ObjectMapper should be injected

    public <T> T get(String key, Class<T> entity) {
        try {
            Object data = redisTemplate.opsForValue().get(key);
            if (data == null) {
                return null;
            }
            return objectMapper.readValue(data.toString(), entity); // ✅ Deserialize properly
        } catch (Exception e) {
            log.error("Exception occurred while fetching from Redis:", e);
            return null;
        }
    }

    public void set(String key, Object value, Long expiry) {
        try {
            String jsonValue = objectMapper.writeValueAsString(value); // ✅ Proper serialization
            redisTemplate.opsForValue().set(key, jsonValue, expiry, TimeUnit.SECONDS);
        } catch (JsonProcessingException e) {
            log.error("Error occurred while saving to Redis:", e);
        }
    }
}
