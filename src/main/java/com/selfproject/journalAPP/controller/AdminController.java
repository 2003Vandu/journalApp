package com.selfproject.journalAPP.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.selfproject.journalAPP.Cache.AppCache;
import com.selfproject.journalAPP.entity.User;
import com.selfproject.journalAPP.service.UserService;

@RestController
@RequestMapping("/admin")
@Tag(name ="Admin Api", description = "Admin can create A user with rolse Both User, Admin")
public class AdminController 
{
	@Autowired
	private AppCache appCache;
	@Autowired
	public UserService userService;

	@GetMapping("/all-users")
	public ResponseEntity<List<User>> getAllUser()
	{
	     List<User> all =	userService.getall();
	 
	     if(all != null &&  ! all.isEmpty())
	     {
		    return new ResponseEntity<>(all,HttpStatus.OK);
	     }
	       return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	@PostMapping("/create-admine-user")
	public void creatUser(@RequestBody User user)
	{
		userService.saveAdmin(user);
	}
	
	
	@GetMapping("/clear-app-cache")
	public String appCache()
	{
	     appCache.init();
	     return "Cache is Refresh";    
	}
}
