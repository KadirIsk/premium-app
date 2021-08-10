package com.mogan.premiumapp.controller;

import com.mogan.premiumapp.jwt.LoginRequest;
import com.mogan.premiumapp.jwt.LoginResult;
import com.mogan.premiumapp.jwt.LoginResultFactory;
import com.mogan.premiumapp.model.User;
import com.mogan.premiumapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("api/authentication")
public class AuthController {
    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "ping", method = RequestMethod.GET)
    public ResponseEntity<String> ping() {
        return new ResponseEntity<>("pong", HttpStatus.OK);
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseEntity<LoginResult> login(@RequestBody LoginRequest loginRequest) {
        User kullanici = userRepository.findByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword());

        LoginResult loginResult = LoginResultFactory.createLoginResult(kullanici);

        return new ResponseEntity<>(loginResult, HttpStatus.OK);
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public ResponseEntity<Void> register(@RequestBody User newUser) {
        User usr = userRepository.findByUsernameAndPassword(newUser.getUsername(), newUser.getPassword());

        if (usr != null) throw new IllegalArgumentException("User already exist!");

        userRepository.save(newUser);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
