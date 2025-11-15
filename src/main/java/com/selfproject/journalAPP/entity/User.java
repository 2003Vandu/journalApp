package com.selfproject.journalAPP.entity;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.mongodb.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Document(collection = "users")
//@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	private ObjectId id;
	   
	@Indexed(unique = true)
	@NonNull
	private String userName;
	
	private String email;
	
	@Field("SentimentalAnalysis")
	private boolean SentimentalAnalysis;
	
	@NonNull
	private String password;
	
	
	@DBRef
	private List<JournalEntry> journalEntries= new ArrayList<>();
	
	private List<String> roles;
	

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<JournalEntry> getJournalEntries() {
		return journalEntries;
	}

	public void setJournalEntries(List<JournalEntry> journalEntries) {
		this.journalEntries = journalEntries;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

//	public String getSentimentalAnalysis() {
//		return SentimentalAnalysis;
//	}
//	
//	public void setSentimentalAnalysis(String sentimentalAnalysis) {
//		SentimentalAnalysis = sentimentalAnalysis;
//	}

	public boolean isSentimentalAnalysis() {
	    return SentimentalAnalysis;
	}

	public void setSentimentalAnalysis(boolean sentimentalAnalysis) {
	    this.SentimentalAnalysis = sentimentalAnalysis;
	}

	
}
