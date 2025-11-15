package com.selfproject.journalAPP.UserScheduler;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.selfproject.journalAPP.Scheduler.UserScheduler;

@Disabled
@SpringBootTest
public class UserSchedulerTest {
	
	@Autowired
	private UserScheduler userScheduler;
	
	@Test
	public void TestfeatchuserandSend_SA_mail()
	{
		userScheduler.featchuserandSend_SA_mail();
	}
}
