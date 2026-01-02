package com.aryan.journalApp.service;

import com.aryan.journalApp.entity.JournalEntry;
import com.aryan.journalApp.entity.User;
import com.aryan.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(JournalEntryService.class);

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName){
        User user = userService.findByUserName(userName);
        JournalEntry saved = journalEntryRepository.save(journalEntry);
        user.getJournalEntries().add(saved);
        userService.saveUser(user);
        logger.info("hahahahahah");
    }
    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry>getAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    public void deleteById(ObjectId id,String userName){
        User user = userService.findByUserName(userName);
        boolean removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
        if(removed){
            userService.saveUser(user);
            journalEntryRepository.deleteById(id);
        }

    }

}
