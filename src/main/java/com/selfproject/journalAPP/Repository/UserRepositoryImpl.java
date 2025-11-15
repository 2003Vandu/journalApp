package com.selfproject.journalAPP.Repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.selfproject.journalAPP.entity.User;

public class UserRepositoryImpl {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	public List<User> getUserForSA() {
		
		Query query = new Query();
		query.addCriteria(Criteria.where("email").regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"));
		
		query.addCriteria(Criteria.where("SentimentalAnalysis").is(true));
		
		List<User> users = mongoTemplate.find(query, User.class);
	
		return users;
		
	
	}  
}
/* 
 * we can use or and operators and Many more 
 * 
 *  *** OR **
 * Criteria criteria = new Criteria();
   query.addCriteria( criteria.orOperator
		(
				Criteria.where("email").exists(false),
				Criteria.where("SentimentalAnalysis").is(true))
		 );
		 
    ***AND***
   Criteria criteria = new Criteria();
   query.addCriteria( criteria.andOperator
		(
				Criteria.where("email").exists(false),
				Criteria.where("SentimentalAnalysis").is(true))
		 );
 * */