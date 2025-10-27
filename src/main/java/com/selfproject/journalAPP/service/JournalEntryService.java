package com.selfproject.journalAPP.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.selfproject.journalAPP.Repository.JournalEntryRepository;
import com.selfproject.journalAPP.entity.JournalEntry;
import com.selfproject.journalAPP.entity.User;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JournalEntryService {
	
	@Autowired
	private JournalEntryRepository journalEntryRepository;
	
	@Autowired
	private UserService userService;
	
	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(JournalEntryService.class);
	
	@Transactional
	public void saveEntry(JournalEntry journalEntry, String userName)
	{
		try {
			System.out.println("Received username: " + userName);
		    User user = userService.findByuserName(userName);
		    System.out.println("Found user: " + user);
		    if (user == null) {
		        throw new IllegalArgumentException("User with username '" + userName + "' not found.");
		    }

		    journalEntry.setDate(LocalDateTime.now());
		    JournalEntry saved = journalEntryRepository.save(journalEntry);
		    user.getJournalEntries().add(saved);
		    
		    userService.saveUser(user);
		}
		catch (Exception e) {
			System.out.println(e);
			throw new RuntimeException("An error occure While saving the entry",e);
		}

	    
	}
	public void saveEntry(JournalEntry journalEntry)
	{
		journalEntryRepository.save(journalEntry);

	    
	}
	
	public List<JournalEntry> getall()
	{ 
		return journalEntryRepository.findAll();
		
	}
	
	public Optional<JournalEntry> findByID(ObjectId id)
	{
		return journalEntryRepository.findById(id);
	}
	
	@Transactional
	public boolean deleteById(ObjectId id, String userName)
	{
		boolean removed = false;
		
        try {
        	
        	User user = userService.findByuserName(userName);
            removed= user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            
            if(removed)
            {
            	userService.saveUser(user);
        		journalEntryRepository.deleteById(id);
            }
			
		} catch (Exception e) {
			log.error("Error",e);
			throw new RuntimeException("An Error occur while deleting entry.",e);
		}
        return removed;
        
		
	}

}

// controller ------> Service -----------> Repository
