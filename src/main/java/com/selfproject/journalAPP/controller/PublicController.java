package com.selfproject.journalAPP.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import com.selfproject.journalAPP.entity.User;
import com.selfproject.journalAPP.service.UserService;
import com.selfproject.journalAPP.Utilis.JwtUtils;

@RestController 
@RequestMapping("/public")
@Slf4j
class PublicController 
{

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

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

    @PostMapping("/Signup")
    public void Signup(@RequestBody User user)
    {
        userService.saveNewUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user)
    {
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());

            String jwt = jwtUtils.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception occur While creatingAuthentatication",e);
            return new ResponseEntity<>("Incorrect userName or password",HttpStatus.BAD_REQUEST);
        }
    }
}

