package com.selfproject.journalAPP.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "Config_journal_app")
@Data
@NoArgsConstructor
public class ConfigjournalAppEntity {

	// fild is used to map data base fild keys 
	@Field("Key")
	private String Key;
	@Field("Value")
	private String Value;
}
