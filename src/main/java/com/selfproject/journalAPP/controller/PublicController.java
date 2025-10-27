package com.selfproject.journalAPP.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.selfproject.journalAPP.entity.User;
import com.selfproject.journalAPP.service.UserService;

@RestController 
@RequestMapping("/public")
class PublicController 
{
	@Autowired
	private UserService userService;
	
	@GetMapping("/Status")
	public String Healthchecker()
	{
		return "Journal APP is running ";
	}
    
	@PostMapping("/create-user")
	public void createUser(@RequestBody User user)
	{
		userService.saveNewUser(user);
	}
}

