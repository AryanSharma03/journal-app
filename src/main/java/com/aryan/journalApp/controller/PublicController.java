package com.aryan.journalApp.controller;

import com.aryan.journalApp.entity.User;
import com.aryan.journalApp.service.UserDetailServiceImpl;
import com.aryan.journalApp.service.UserService;
import com.aryan.journalApp.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;
    @Autowired
    private UserDetailServiceImpl userDetailsService;
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/health-check")
    public String healthCheck() {
        return "Ok";
    }

    @PostMapping("/signup")
    public void signup(@RequestBody User user) {
        userService.saveNewUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        try {
            AuthenticationManager authenticationManager =
                    authenticationConfiguration.getAuthenticationManager();

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUserName(),
                            user.getPassword()
                    )
            );

            UserDetails userDetails =
                    userDetailsService.loadUserByUsername(user.getUserName());

            String jwt = jwtUtil.generateToken(userDetails.getUsername());

            return new ResponseEntity<>(jwt, HttpStatus.OK);

        } catch (Exception e) {
            log.error("Exception occurred while createAuthenticationToken ", e);
            return new ResponseEntity<>("Incorrect username or password",
                    HttpStatus.BAD_REQUEST);
        }
    }
}