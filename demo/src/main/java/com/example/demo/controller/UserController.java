package com.example.demo.controller;

import com.example.demo.modal.User;
import com.example.demo.response.MessageResponse;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.example.demo.exceptions.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserService userService;
    @GetMapping("/id/{id}")

        public ResponseEntity<User> getUserByIdHandler(@PathVariable Integer id) throws UserException {
            // Lấy thông tin user từ database theo id
            User user = userService.findUserById(id);

            // Nếu không tìm thấy user, trả về HTTP status 404 Not Found
            if (user == null) {
                return ResponseEntity.notFound().build();
            }

            // Nếu tìm thấy user, trả về thông tin user và HTTP status 200 OK
            return ResponseEntity.ok(user);
        }
    @GetMapping("/username/{username}")
    public ResponseEntity<User> findUserByUsernameHandler(@PathVariable String username) throws UserException {
        User user  = userService.findUserByUsername(username);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
    @PutMapping("/follow/{followUserId}")
    public ResponseEntity<String> followUserHandler(@PathVariable Integer followUserId, @RequestHeader("Authorization") String token) throws UserException {
        User user = userService.findUserProfile(token);
        String message = userService.followUser(user.getId(), followUserId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        return new ResponseEntity<String>(message, headers, HttpStatus.OK);
    }
    @PutMapping("/unfollow/{followUserId}")
    public ResponseEntity<String> unFollowUserHandler(@PathVariable Integer followUserId, @RequestHeader("Authorization") String token) throws UserException {
        User user = userService.findUserProfile(token);
        String message = userService.unFollowUser(user.getId(), followUserId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        return new ResponseEntity<String>(message, headers, HttpStatus.OK);
    }
    @GetMapping("/req")
    public ResponseEntity<User> findUserProfileHandler(@RequestHeader("Authorization") String token) throws UserException {
        User user = userService.findUserProfile(token);
        return new ResponseEntity<User>(user,HttpStatus.OK);
    }
    @GetMapping("/m/{userIds}")
    public ResponseEntity<List<User>> findUserByIdsHandler(@PathVariable List<Integer> userIds) throws UserException {
        List<User> users = userService.findUserByIds(userIds);
        return new  ResponseEntity<List<User>>(users,HttpStatus.OK);

    }
    @GetMapping("/seach")
    public ResponseEntity<List<User>> searchUserHandler(@RequestParam("q") String query) throws UserException {
          List<User> users = userService.searchUser(query);
          return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }
    @PutMapping("/account/edit")
public  ResponseEntity<User> updateUserHandler(@RequestHeader("Authorization") String token, @RequestBody User user) throws UserException {

        User reqUser = userService.findUserProfile(token);
        User updateUser = userService.updateUserDetails(user,reqUser);


    return new ResponseEntity<User>(updateUser,HttpStatus.OK);

}







}
