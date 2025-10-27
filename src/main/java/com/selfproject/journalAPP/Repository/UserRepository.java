package com.selfproject.journalAPP.Repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.selfproject.journalAPP.entity.User;

public interface UserRepository extends MongoRepository<User, ObjectId>{
	
	User findByuserName(String username);
	
	void deleteByUserName(String username);

}
