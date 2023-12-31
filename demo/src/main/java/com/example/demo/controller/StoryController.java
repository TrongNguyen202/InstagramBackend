package com.example.demo.controller;

import com.example.demo.exceptions.StoryException;
import com.example.demo.exceptions.UserException;
import com.example.demo.modal.Story;
import com.example.demo.modal.User;
import com.example.demo.service.StoryService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stories")
public class StoryController {
    @Autowired
    private UserService userService;
    @Autowired
    private StoryService storyService;

    @PostMapping("/create")
public ResponseEntity<Story> createStoryHandler(@RequestBody Story story, @RequestHeader("Authorization") String token ) throws UserException {
        User user = userService.findUserProfile(token);
        Story createStory = storyService.createStory(story, user.getId());
        return new ResponseEntity<Story>(story, HttpStatus.OK);
}
@GetMapping("/{userId}")
public ResponseEntity<List<Story>> findAllStoryByUserIdHandler(@PathVariable Integer userId) throws UserException, StoryException {
    User user = userService.findUserById(userId);
    List<Story> stories = storyService.findStoryByUserId(userId);
    return new ResponseEntity<List<Story>>(stories,HttpStatus.OK);
}

}
