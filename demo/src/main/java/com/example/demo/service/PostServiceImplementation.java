package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.exceptions.PostException;
import com.example.demo.exceptions.UserException;
import com.example.demo.modal.Post;
import com.example.demo.modal.User;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service

public class PostServiceImplementation implements PostService{
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Override
    public Post createPost(Post post, Integer userId) throws UserException {
        User user = userService.findUserById(userId);
        if (user == null) {
            throw new UserException("User not found with ID: " + userId);
        }
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setUserImage(user.getImage());
        userDto.setName(user.getName());
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        post.setUser(userDto);
        Post createPost = postRepository.save(post);
        return createPost;
    }

    @Override
    public String deletePost(Integer postId, Integer userId) throws UserException, PostException {
        Post post = findPostById(postId);
        User user =  userService.findUserById(userId);
        if(post.getUser().getId().equals(user.getId())){
            postRepository.deleteById(postId);
            return "Post deleted successfully";
        }

        throw new PostException("you can't delete other user's post");
    }

    @Override
    public List<Post> findPostByUserId(Integer userId) throws UserException {
        List<Post> posts = postRepository.findByUserId(userId);
        if(posts.size()==0){
            throw new UserException("this user does not have any post");
        }

        return posts;
    }

    @Override
    public Post findPostById(Integer postId) throws PostException {
        Optional<Post> post = postRepository.findById(postId);
        if(!post.isPresent()){
            throw new PostException("this post does'nt exit with id"+postId);
        }

        return post.get();
    }

    @Override
    public List<Post> findAllPostByUserIds(List<Integer> userIds) throws PostException, UserException {
        List<Post> posts = postRepository.findAllPostByUserIds(userIds);
        if(posts.size()==0){
            throw new UserException("No post available");
        }



        return posts;
    }

    @Override
    public User savePost(Integer postId, Integer userId) throws PostException, UserException {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);
        if(!user.getSavedPost().contains(post)){
            user.getSavedPost().add(post);
           return userRepository.save(user);

        }
        return user;

    }

    @Override
    public User unSavePost(Integer postId, Integer userId) throws PostException, UserException {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);
        if(user.getSavedPost().contains(post)){
            user.getSavedPost().remove(post);
            return userRepository.save(user);

        }
        return user;

    }

    @Override
    public Post likePost(Integer postId, Integer userId) throws PostException, UserException {
        Post post = findPostById(postId);
        User user =  userService.findUserById(userId);
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setUserImage(user.getImage());
        userDto.setName(user.getName());
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        post.getLikedByUsers().add(userDto);


        return postRepository.save(post);
    }

    @Override
    public Post unLikePost(Integer postId, Integer userId) throws PostException, UserException {
        Post post = findPostById(postId);
        User user =  userService.findUserById(userId);
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setUserImage(user.getImage());
        userDto.setName(user.getName());
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        post.getLikedByUsers().remove(userDto);


        return postRepository.save(post);
    }
}
