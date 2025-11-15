package com.selfproject.journalAPP.Repository;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mongodb.assertions.Assertions;
import com.selfproject.journalAPP.entity.User;

@Disabled
@SpringBootTest
public class userRepositoryImplTest {

	
	@Autowired
	private UserRepositoryImpl userRepositoryImpl;
	
	@Disabled
	@Test
	public void TestSaveNewUserorCustomeArgument()
	{
       List<User> userString= userRepositoryImpl.getUserForSA();
	   
	  for(User user : userString)
	  {
		 System.out.println(user.getUserName());
	  }
		
		for (User user : userString) {
	        System.out.println("Username: " + user.getUserName() + ", email: " + user.getEmail());
	    }

		Assertions.assertNotNull(userRepositoryImpl.getUserForSA());

	}
}
