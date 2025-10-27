package com.selfproject.journalAPP.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.selfproject.journalAPP.Repository.UserRepository;
import com.selfproject.journalAPP.entity.User;
import com.selfproject.journalAPP.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController 
{
	
	@Autowired
    private UserService userService;
	
	@Autowired
	private UserRepository userRepository;

	
	@PutMapping
	public ResponseEntity<?> updateuser(@RequestBody User user)
	{
		org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
	    User userInDB = userService.findByuserName(userName);
	    userInDB.setUserName(user.getUserName());
   	    userInDB.setPassword(user.getPassword());
   	    userService.saveNewUser(userInDB);       
	    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping
    public ResponseEntity<?> deleteUserByID(@RequestBody User user)
    {
		org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		userRepository.deleteByUserName(authentication.getName());
    	return new ResponseEntity<>(HttpStatus.OK);
    }

}
