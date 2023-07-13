package com.example.demo.controller;

import com.example.demo.exceptions.UserException;
import com.example.demo.modal.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
public class AuthController {

 @Autowired
 UserService userService;

 @Autowired
 UserRepository userRepo;

 @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")

 @PostMapping("/signup")
 public ResponseEntity<User> resisterUserHandler(@RequestBody User user) throws UserException {
  User createUser = userService.registerUser(user);
  return new ResponseEntity<User>(createUser, HttpStatus.OK);
 }

 @CrossOrigin(origins = "http://localhost:3000/")
 @PostMapping("/signin")
 public ResponseEntity<User> signinHandler(Authentication auth) throws BadCredentialsException {
  Optional<User> opt = userRepo.findByEmail(auth.getName());

  if (opt.isPresent()) {
   return new ResponseEntity<User>(opt.get(), HttpStatus.ACCEPTED);
  }

  throw new BadCredentialsException("invalid username or password");
 }
}