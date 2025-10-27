package com.selfproject.journalAPP.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.selfproject.journalAPP.Repository.UserRepository;
import com.selfproject.journalAPP.entity.User;
import com.selfproject.journalAPP.service.UserService;

@SpringBootTest
public class userServiceTests 
{
	@Autowired
	private UserService userService;
	
	
	@Autowired
	private UserRepository userRepository;
	
	
	
	@ParameterizedTest
	@ArgumentsSource(userArgumentProvider.class)
	public void TestSaveNewUserorCustomeArgument(User user)
	{
		assertTrue(userService.saveNewUser(user));
		
	}
	

	@Disabled
	@ParameterizedTest
	@ValueSource( strings = {
		"Vandesh",
		"Amruta",
		"Rohit", 
	})
	public void findByuserNameTest(String user)
	{
		assertNotNull(userRepository.findByuserName(user),"faild for "+user);
	}
	
    @Disabled
	@ParameterizedTest
	@CsvSource(
			{
				"1,2,3",
				"1,1,2",
				"1,2,3"
			}
			)
	public void test(int a, int b, int exepted)
	{
		assertEquals(exepted, a+b );
		
	}
	
	@Disabled
	@Test
	public void findByuserNameTest1()
	{
		User user = userRepository.findByuserName("Vandesh");
	    assertTrue(!user.getJournalEntries().isEmpty()); 
	}
	
	
	
	
}
