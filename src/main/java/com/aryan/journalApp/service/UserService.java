package com.aryan.journalApp.service;

import com.aryan.journalApp.entity.User;
import com.aryan.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveNewUser(User user){
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.getRoles().add("USER");   // 👈 default role
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
    }   public void saveAdmin(User user){
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.getRoles().add("ADMIN");   // 👈 default role
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    public List<User>getAll(){
        return userRepository.findAll();
    }

    public Optional<User> findById(ObjectId id){
        return userRepository.findById(id);
    }

    public void deleteById(ObjectId id){
        userRepository.deleteById(id);
    }

    public User findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }


}
