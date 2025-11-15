package com.selfproject.journalAPP.service;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.selfproject.journalAPP.api.response.WeatherResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RedisService {
	
	/*
	 * @Service used to intrect with Redis Directly 
	 * 
	 * */
	
	@Autowired
	private RedisTemplate redisTemplate;

	public <T> T get(String key, Class<T> entityclass )
	{
         try {
        	 Object o = redisTemplate.opsForValue().get(key);		
             ObjectMapper mapper = new ObjectMapper();
             
             return mapper.readValue(o.toString(), entityclass);
		} catch (Exception e) {
		      log.error("Exception");
		      return null;
		}
	}
	
	

	public void set(String key,Object o, long ttl) // ttl time to leave redis data store
	{
         
         try {
        	 ObjectMapper mapper = new ObjectMapper();
        	 String jsonValue = mapper.writeValueAsString(o);
        	 redisTemplate.opsForValue().set(key,jsonValue,ttl,TimeUnit.SECONDS);	
        	 
		} catch (Exception e) {
		      log.error("Exception occure in RedisService while Seting a key value in Redis");
		      
		}
	}
}
