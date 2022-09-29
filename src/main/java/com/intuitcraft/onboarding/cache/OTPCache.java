package com.intuitcraft.onboarding.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Base64;

@Component
public class OTPCache {

    private final String SERVICE_NAME = "ONBOARD";

    @Value("${redis.otpToken.ttl}")
    private int ttl;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    ObjectMapper mapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(OTPCache.class);

    public int getIncorrectOtpCount(String otpToken){
        String cacheKey = getKey(otpToken);
        Object count =  redisTemplate.opsForValue().get(cacheKey);
        if(count != null)
            return Integer.parseInt((String) count);
        return 0;
    }

    public String getKey(String otpToken) {
        return Base64.getEncoder().encodeToString((SERVICE_NAME + otpToken).getBytes());
    }

    public void cacheData(String key, Object value, Duration duration) {
        String cacheKey = getKey(key);
        try {
            String json = mapper.writeValueAsString(value);
            redisTemplate.opsForValue().set(cacheKey, json, duration);
        } catch (JsonProcessingException e) {
            LOGGER.error("Error while caching the data with key {} ", cacheKey);
        }
    }

    public <T> T getData(String otpToken, Class<T> valueType) {
        String cacheKey = otpToken;
        try {
            Object value = redisTemplate.opsForValue().get(cacheKey);
            if (value != null) {
                LOGGER.info("Getting data from cache for {}",cacheKey);
                return mapper.readValue((String) value, valueType);
            }
            return null;
        }catch (JsonProcessingException e) {
            LOGGER.error("No data found with key {} ", cacheKey);
            return null;
        }
    }

    public int updateOtpCount(String key) {
        String cacheKey = getKey(key);
        Long otp = null;
        try{
            otp = redisTemplate.opsForValue().increment(cacheKey);
            redisTemplate.expire(cacheKey, Duration.ofMinutes(ttl));
        }catch (Exception e){
            LOGGER.error("Error while caching the data with key {} ", cacheKey);
        }
        return Math.toIntExact(otp);
    }

    public void clearCache(String otpCountKey) {
        String cacheKey = getKey(otpCountKey);
        redisTemplate.opsForValue().getAndDelete(cacheKey);
        LOGGER.info("User otp validated, cache cleared");
    }
}
