package com.selfproject.journalAPP.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.selfproject.journalAPP.Repository.UserRepository;
import com.selfproject.journalAPP.entity.User;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public static final PasswordEncoder passwordEncode = new BCryptPasswordEncoder();
	
	
	public Boolean saveNewUser(User user)
	{
		try {
			user.setPassword(passwordEncode.encode(user.getPassword()));
			user.setRoles(Arrays.asList("USER"));
			userRepository.save(user); 
			
			return true;
		} catch (Exception e) {
			log.error("Error occure creating duplicate {} : ",user.getUserName(),e);
//			log.warn("Not possible to creat again ");
//			log.info("user name Already exist");
         	log.debug("user name Already exist");
//			log.trace("user name Already exist");
			return false;
		}
		
	}
	public void saveAdmin(User user)
	{
		user.setPassword(passwordEncode.encode(user.getPassword()));
		user.setRoles(Arrays.asList("USER","ADMIN"));
		userRepository.save(user); 
	}
	
	public void saveUser(User user)
	{
		userRepository.save(user); 
	}
	
	
	public List<User> getall()
	{
		return userRepository.findAll(); 
	}
	
	public Optional<User> findByID(ObjectId id)
	{
		return userRepository.findById(id);
	}
	
	public void deleteById(ObjectId id)  
	{  	
		userRepository.deleteById(id);  	
	}
	
	public User findByuserName(String username)
	{
		return userRepository.findByuserName(username);
		
	}
}
