package com.aryan.journalApp.repository;

import com.aryan.journalApp.entity.JournalEntry;
import com.aryan.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {

    User findByUserName(String username);

    void deleteByUserName(String username);

}
