package com.selfproject.journalAPP.Repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.selfproject.journalAPP.entity.ConfigjournalAppEntity;

public interface ConfigjournalAppRepository extends MongoRepository<ConfigjournalAppEntity, ObjectId> {

}
