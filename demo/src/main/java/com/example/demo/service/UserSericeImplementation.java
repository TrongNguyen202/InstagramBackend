package com.example.demo.service;
import com.example.demo.config.JwtTokenGeneratorFilter;
import com.example.demo.security.JwtTokenClaims;
import com.example.demo.security.JwtTokenProvider;
import org.modelmapper.ModelMapper;
import com.example.demo.dto.UserDto;
import com.example.demo.exceptions.UserException;
import com.example.demo.modal.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service

public class UserSericeImplementation implements  UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;



    @Override
    public User registerUser(User user) throws UserException {
        Optional<User> isEmailExit = userRepository.findByEmail(user.getEmail());
        if(isEmailExit.isPresent()){
            throw new UserException("Email already exit");
        }
        Optional<User> isUsernameExit = userRepository.findByUsername(user.getUsername());
        if(isUsernameExit.isPresent()){
            throw new UserException("Username already taken...");
        }
        if(user.getEmail()==null||user.getPassword()==null||user.getUsername()==null||user.getName()==null){
           throw  new UserException("All input are required");
        }
        User newuser = new User();
        newuser.setEmail(user.getEmail());
        newuser.setUsername(user.getUsername());
        newuser.setPassword(passwordEncoder.encode( user.getPassword()));
        newuser.setName(user.getName());

        return  userRepository.save(newuser) ;

    }

    @Override
    public User findUserById(Integer userId) throws UserException {
        Optional<User> opt = userRepository.findById(userId);
        if(opt.isPresent()){
            return opt.get();
        }

            throw new UserException("User is not exit"+ userId);



    }

    @Override
    public User findUserProfile(String token) throws UserException {
        token = token.substring(7);
        JwtTokenClaims jwtTokenClaims = jwtTokenProvider.getClaimsFromToken(token);
        String email = jwtTokenClaims.getUsername();
        Optional<User> opt = userRepository.findByEmail(email);
        if (opt.isPresent()){
            return opt.get();
        }
        throw new  UserException("invalid token...");



    }

    @Override
    public User findUserByUsername(String username) throws UserException {
        Optional<User> opt = userRepository.findByUsername(username);
        if(opt.isPresent()){
            return opt.get();
        }
        throw  new UserException("user not exit with username"+username);

    }

    @Override
    public String followUser(Integer reqUserId, Integer followUserId) throws UserException {
        User reqUser = findUserById(reqUserId);
        User followUser = findUserById(followUserId);
        if (reqUser == null || followUser == null) {
            throw new UserException("User not found");
        }
        UserDto follower = new UserDto();
        follower.setEmail(reqUser.getEmail());
        follower.setId(reqUser.getId());
        follower.setName(reqUser.getName());
        follower.setUserImage(reqUser.getImage());
        follower.setUsername(reqUser.getUsername());
        UserDto following = new UserDto();
        following.setEmail(followUser.getEmail());
        following.setId(followUser.getId());
        following.setName(followUser.getName());
        following.setUserImage(followUser.getImage());
        following.setUsername(followUser.getUsername());
        reqUser.getFollowing().add(following);
        followUser.getFollower().add(follower);
        userRepository.save(reqUser);
        userRepository.save(followUser);
        return "You are now following " + followUser.getName();
    }

    @Override
    public String unFollowUser(Integer reqUserId, Integer followUserId) throws UserException {
        User reqUser = findUserById(reqUserId);
        User followUser = findUserById(followUserId);
        if (reqUser == null || followUser == null) {
            throw new UserException("User not found");
        }
        UserDto follower = new UserDto();
        follower.setEmail(reqUser.getEmail());
        follower.setId(reqUser.getId());
        follower.setName(reqUser.getName());
        follower.setUserImage(reqUser.getImage());
        follower.setUsername(reqUser.getUsername());
        UserDto following = new UserDto();
        following.setEmail(followUser.getEmail());
        following.setId(followUser.getId());
        following.setName(followUser.getName());
        following.setUserImage(followUser.getImage());
        following.setUsername(followUser.getUsername());
        reqUser.getFollowing().remove(following);
        followUser.getFollower().remove(follower);
        userRepository.save(reqUser);
        userRepository.save(followUser);
        return "You have unfollowed " + followUser.getName();
    }

    @Override
    public List<User> findUserByIds(List<Integer> userIds) throws UserException {
        List<User> users = userRepository.findAllUsersByUserIds(userIds);
        return users;
     }

    @Override
    public List<User> searchUser(String query) throws UserException {
       List<User> users = userRepository.findByQuery(query);
       if(users.size()==0){
           throw new UserException("user not found");

       }
       return users;
    }

    @Override
    public User updateUserDetails(User updatedUsers, User exitingUser) throws UserException {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(updatedUsers, exitingUser);
         if(updatedUsers.getId().equals(exitingUser.getId())){
             return userRepository.save(updatedUsers);
         }
         throw new UserException("You can't update this user");

    }
}
