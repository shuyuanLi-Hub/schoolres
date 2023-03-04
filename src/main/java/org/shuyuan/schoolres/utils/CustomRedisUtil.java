package org.shuyuan.schoolres.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class CustomRedisUtil
{
    public final RedisTemplate<String, String> redisTemplate;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public CustomRedisUtil(RedisTemplate<String, String> redisTemplate)
    {
        this.redisTemplate = redisTemplate;
    }

    public <T> T get(KeyPrefix keyPrefix, String key, Class<T> clazz)
    {
        String realKey = keyPrefix.getPrefix() + key;

        String value = redisTemplate.opsForValue().get(realKey);

        return stringToBean(value, clazz);
    }

    public <T> Boolean set(KeyPrefix prefix, String key, T value)
    {
        String str = null;

        try
        {
            str = beanToString(value);
        }
        catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }

        if (str == null || str.length() <= 0)
        {
            return false;
        }

        String realKey = prefix.getPrefix() + key;

        int seconds = prefix.expireSeconds();

        if (seconds <= 0)
        {
            redisTemplate.opsForValue().set(realKey, str);
        }
        else
        {
            redisTemplate.opsForValue().set(realKey, str, Duration.ofSeconds(seconds));
        }

        return true;
    }

    public Boolean exists(KeyPrefix prefix, String key)
    {
        String realKey = prefix.getPrefix() + key;

        return redisTemplate.hasKey(realKey);
    }

    public Boolean delete(KeyPrefix prefix, String key)
    {
        String realKey = prefix.getPrefix() + key;

        return redisTemplate.delete(realKey);
    }

    public Long incr(KeyPrefix prefix, String key)
    {
        String realKey = prefix.getPrefix() + key;

        return redisTemplate.opsForValue().increment(realKey);
    }

    public static <T> String beanToString(T value) throws JsonProcessingException
    {
        if (value == null)
        {
            return null;
        }

        Class clazz = value.getClass();

        if (clazz == int.class || clazz == Integer.class)
        {
            return "" + value;
        }

        if (clazz == long.class || clazz == Long.class)
        {
            return "" + value;
        }

        if (clazz == String.class)
        {
            return (String) value;
        }

        return objectMapper.writeValueAsString(value);
    }

    @SneakyThrows
    public static <T> T stringToBean(String str, Class<T> clazz)
    {
        if (str == null || str.length() <= 0 || clazz == null)
        {
            return null;
        }

        if (clazz == int.class || clazz == Integer.class)
        {
            return (T) Integer.valueOf(str);
        }

        if (clazz == long.class || clazz == Long.class)
        {
            return (T) Long.valueOf(str);
        }

        if (clazz == String.class)
        {
            return (T) str;
        }

        return objectMapper.readValue(str, clazz);
    }
}
