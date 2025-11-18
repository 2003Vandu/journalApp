package com.selfproject.journalAPP.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.selfproject.journalAPP.JournalApplication;
import com.selfproject.journalAPP.entity.JournalEntry;
import com.selfproject.journalAPP.entity.User;
import com.selfproject.journalAPP.service.JournalEntryService;
import com.selfproject.journalAPP.service.UserService;


@RestController
@RequestMapping("/journal")
@Tag(name ="Journal Data APIS", description = "Read ,Update, delete, user journals")
public class JournalEntryControllerv2 {

    @SuppressWarnings("unused")
	private final JournalApplication journalApplication;
	
	
	@Autowired
	private JournalEntryService journalEntryService;
	
	@Autowired
	private UserService userService;


    JournalEntryControllerv2(JournalApplication journalApplication) {
        this.journalApplication = journalApplication;
    }
	

	@GetMapping
    @Operation(summary = "Get all journal Entries of user")
	public ResponseEntity<?> getAllJoyrnalEntriesOFUSer()
	{
		
		org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		
		User user = userService.findByuserName(userName);
		List<JournalEntry> allEntries = user.getJournalEntries();
		
		if (allEntries.isEmpty()) {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }

	    return new ResponseEntity<>(allEntries, HttpStatus.OK);

	}
	

	@PostMapping
    @Operation(summary = "Create journal Entrie of user")
	public ResponseEntity<?> createEntry(@RequestBody JournalEntry myEntry) {
	    try {
	    	
	    	org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String userName = authentication.getName();

	        journalEntryService.saveEntry(myEntry, userName);
	        return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	    } catch (Exception e) {
	        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }
	}

	// change for swagger
	@GetMapping("id/{myId}")
    @Operation(summary = "Get  journal Entries of user By ID")
	public ResponseEntity<JournalEntry> getJournalEntryByid(@PathVariable String myId) {

        ObjectId objectId = new ObjectId(myId);
	    org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String userName = authentication.getName();

	    User user = userService.findByuserName(userName);
	    List<JournalEntry> collect = user.getJournalEntries()
	                            .stream()
                                                      //change is  this <objectId>
	                            .filter(x -> x.getId().equals(objectId))
	                            .collect(Collectors.toList());

	    if(!collect.isEmpty())
	    {
            //change is  this <objectId>
	    	Optional<JournalEntry> journalEntry =journalEntryService.findByID(objectId);
	    	if(journalEntry.isPresent())
			 {
				 return new ResponseEntity<>(journalEntry.get(),HttpStatus.OK);
			 }

	    }
		 return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		}

	
	/*
	 * cascading delete  {@DeleteMapping("id/{myId}")}  if we just use to delete this means only id jouralEntry get delete but not in User collection 
	 * To delete from users collations we have to delete manually passing userName And id to end poin API 
	 * @DeleteMapping("id/{userName}/{myId}") Like this   
	 *
	 * */
	@DeleteMapping("id/{myId}")
    @Operation(summary = "Delete  journal Entries of user By ID")
	public ResponseEntity<?> deleteJournalEntryByid(@PathVariable ObjectId myId) {
		
		org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String userName = authentication.getName();
	    
		boolean removed = journalEntryService.deleteById(myId,userName);
		if(removed){
			
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PutMapping("id/{myId}")
    @Operation(summary = "update journal Entries of user By ID")
    public ResponseEntity<?> updatEntryByid(@PathVariable ObjectId myId,@RequestBody JournalEntry newEntry) {
		
		org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String userName = authentication.getName();
	    User user = userService.findByuserName(userName);
	    List<JournalEntry> collect = user.getJournalEntries()  
	                            .stream()
	                            .filter(x -> x.getId().equals(myId))
	                            .collect(Collectors.toList());
	    if(!collect.isEmpty())
	    {
	    	Optional<JournalEntry> journalEntry =journalEntryService.findByID(myId);
	    	if(journalEntry.isPresent())
			 {
	    		JournalEntry old = journalEntry.get();
	    		
	    		if (old != null && newEntry != null) 
	    		{
	    		    // Update title
	    		    if (newEntry.getTitle() != null && !newEntry.getTitle().isEmpty()) {
	    		        old.setTitle(newEntry.getTitle());
	    		    } 

	    		    // Update contact
	    		    if (newEntry.getContact() != null && !newEntry.getContact().isEmpty()) {
	    		        old.setContact(newEntry.getContact());
	    		    } 

	    		    journalEntryService.saveEntry(old);
	    			return new ResponseEntity<>(old,HttpStatus.OK);
			   }
	    	}
	    }
		
		return new  ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		
	}
	
}

// og mapping by id
/*
       @GetMapping("id/{myId}")
      @Operation(summary = "Get  journal Entries of user By ID")
  	public ResponseEntity<JournalEntry> getJournalEntryByid(@PathVariable ObjectId myId) {


  	    org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
  	    String userName = authentication.getName();

  	    User user = userService.findByuserName(userName);
  	    List<JournalEntry> collect = user.getJournalEntries()
  	                            .stream()
  	                            .filter(x -> x.getId().equals(myId))
  	                            .collect(Collectors.toList());

  	    if(!collect.isEmpty())
  	            {
  	    	Optional<JournalEntry> journalEntry =journalEntryService.findByID(myId);
	    	if(journalEntry.isPresent())
			 {
				 return new ResponseEntity<>(journalEntry.get(),HttpStatus.OK);
			 }

  	    }
   		 return new ResponseEntity<>(HttpStatus.NOT_FOUND);

 		}
 */

/*
* // change for swagger
	@GetMapping("id/{myId}")
    @Operation(summary = "Get  journal Entries of user By ID")
	public ResponseEntity<JournalEntry> getJournalEntryByid(@PathVariable String myId) {

        ObjectId objectId = new ObjectId(myId);


	    org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String userName = authentication.getName();

	    User user = userService.findByuserName(userName);
	    List<JournalEntry> collect = user.getJournalEntries()
	                            .stream()
                //change is  this <objectId>
	                            .filter(x -> x.getId().equals(objectId))
	                            .collect(Collectors.toList());

	    if(!collect.isEmpty())
	    {
            //change is  this <objectId>
	    	Optional<JournalEntry> journalEntry =journalEntryService.findByID(objectId);
	    	if(journalEntry.isPresent())
			 {
				 return new ResponseEntity<>(journalEntry.get(),HttpStatus.OK);
			 }

	    }
		 return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		}
* */

