package com.aryan.journalApp.service;


import com.aryan.journalApp.api.response.WeatherResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    public <T> T get(String key, Class<T> entityClass) {
        try {
            Object o = redisTemplate.opsForValue().get(key);

            if (o == null) {
                return null;   // cache miss
            }

            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(o.toString(), entityClass);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void set(String key, Object value, Long ttl) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(value);

            redisTemplate
                    .opsForValue()
                    .set(key, json, ttl, TimeUnit.SECONDS);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
