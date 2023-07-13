package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.exceptions.StoryException;
import com.example.demo.exceptions.UserException;
import com.example.demo.modal.Story;
import com.example.demo.modal.User;
import com.example.demo.repository.StoryRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StoryServiceImplementation implements StoryService {
@Autowired
private StoryRepository storyRepository;
@Autowired
private UserService userService;
@Autowired
    UserRepository userRepository;
public Story createStory(Story story, Integer userId) throws UserException {
    User user = userService.findUserById(userId);
    UserDto userDto = new UserDto();
    userDto.setUserImage(user.getImage());
    userDto.setId(user.getId());
    userDto.setName(user.getName());
    userDto.setEmail(user.getName());
    userDto.setUsername(user.getUsername());
    story.setUser(userDto);
    story.setTimeStamp(LocalDateTime.now());
    user.getStories().add(story);
    userRepository.save(user);
    return storyRepository.save(story);


}
public List<Story> findStoryByUserId(Integer userId) throws UserException, StoryException {
    User user = userService.findUserById(userId);
    List<Story> stories = user.getStories();
    if(stories.size()==0){
        throw new StoryException("this user does not have any story");
    }
    return stories;
}



}
