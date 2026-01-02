package com.aryan.journalApp.cache;


import com.aryan.journalApp.entity.ConfigJournalAppEntity;
import com.aryan.journalApp.repository.ConfigJournalAppRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    public enum keys{
        WEATHER_API;
    }

    @Autowired
    private ConfigJournalAppRepository configJournalAppRepository;

    public Map<String,String>APP_CACHE;


    @PostConstruct
    public void init(){
        APP_CACHE = new HashMap<>();
        List<ConfigJournalAppEntity>all = configJournalAppRepository.findAll();
        for (ConfigJournalAppEntity configJournalAppEntity : all){
            APP_CACHE.put(configJournalAppEntity.getKey(),configJournalAppEntity.getValue());
        }
    }
}
