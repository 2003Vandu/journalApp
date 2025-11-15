package com.selfproject.journalAPP.Service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@Disabled
@SpringBootTest
public class RedisTest 
{
	@Autowired
	private RedisTemplate redisTemplate;
	
	 @SuppressWarnings("unchecked")
	 @Test
     void testSendMail()
     { 
    	 redisTemplate.opsForValue().set("email", "vandu@gmail.com");
    	 
    	 Object email = redisTemplate.opsForValue().get("email");
    	 
    	 System.out.println("Email is set vie spring boot :> "+email);
    	
     }
	 @Test
     void testsetName()
     {
    	 
    	 Object name = redisTemplate.opsForValue().get("name");
    	 
    	 System.out.println("Name is set in Redis using cli :> "+name);
 
     }
}
