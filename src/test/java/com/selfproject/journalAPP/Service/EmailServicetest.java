package com.selfproject.journalAPP.Service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.selfproject.journalAPP.service.EmailService;

@Disabled
@SpringBootTest
public class EmailServicetest {
	
	@Autowired
	private EmailService emailService;
	
	@Disabled
	@Test
	void testsendMail()
	{
		emailService.sendMail
		        (
				"patilvandesh301@gmail.com",
				"testing java mail sender ", 
				"hi vandesh how are you this is a java Springboot mail trasfer "
				);
	}

}
