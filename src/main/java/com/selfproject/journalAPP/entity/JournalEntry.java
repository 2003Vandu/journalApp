package com.selfproject.journalAPP.entity;


import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

// also called as plane old java object 



@Document(collection = "journal_entries")
//@Getter
//@Setter
//@Data
public class JournalEntry 
{
	@Id
	private ObjectId id; 
    
	@NonNull
	private String title;
	
	private String contact;
	
	private LocalDateTime date;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	
	
	

}
