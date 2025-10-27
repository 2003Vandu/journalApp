package com.selfproject.journalAPP.Service;

import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import com.selfproject.journalAPP.Repository.UserRepository;
import com.selfproject.journalAPP.entity.User;
import com.selfproject.journalAPP.service.UserDetailsServiceImpl;

@SpringBootTest
public class UserDetailsServiceImplTest {
	
	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Mock
	private UserRepository userRepository;
	
	@SuppressWarnings("deprecation")
	@BeforeEach
	void setup()
	{
		
		MockitoAnnotations.initMocks(this);
		
	}
	
	@Test
	void loadUserByUsernameTest(){
		
		when( userRepository.findByuserName(ArgumentMatchers.anyString()))
		.thenReturn(
				User.builder().userName("Vandesh")
				              .password("Vandesh")
				              .roles(new ArrayList<>())
				              .build());
	UserDetails user =	userDetailsServiceImpl.loadUserByUsername("Vandesh");
		
	}

}
