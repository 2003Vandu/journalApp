package com.selfproject.journalAPP.Cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.selfproject.journalAPP.Repository.ConfigjournalAppRepository;
import com.selfproject.journalAPP.entity.ConfigjournalAppEntity;

import jakarta.annotation.PostConstruct;

@Component
public class AppCache {
	
	
	public enum keys {
      
		weather_api;
	}
	
	@Autowired
	private ConfigjournalAppRepository configjournalAppRepository;
	
	public Map<String , String > appCache;
	
	
	@PostConstruct
	public void init()
	{
		appCache= new HashMap<>();
		
		List<ConfigjournalAppEntity> all = configjournalAppRepository.findAll();
		
		for (ConfigjournalAppEntity configjournalAppEntity : all) {
			
			appCache.put(configjournalAppEntity.getKey(), configjournalAppEntity.getValue());
		}
		System.out.println("AppCache loaded: " + appCache);

	}
 
}
