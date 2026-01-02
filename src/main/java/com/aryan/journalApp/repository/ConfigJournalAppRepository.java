package com.aryan.journalApp.repository;

import com.aryan.journalApp.entity.ConfigJournalAppEntity;
import com.aryan.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigJournalAppRepository extends MongoRepository<ConfigJournalAppEntity, ObjectId> {

}
