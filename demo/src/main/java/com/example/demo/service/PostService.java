package com.example.demo.service;

import com.example.demo.exceptions.PostException;
import com.example.demo.exceptions.UserException;
import com.example.demo.modal.Post;
import com.example.demo.modal.User;

import java.util.List;

public interface PostService {
    public Post createPost(Post post,Integer userId) throws UserException ;
    public String deletePost(Integer postId, Integer userId) throws UserException, PostException;
    public List<Post> findPostByUserId(Integer userId) throws UserException;
    public Post findPostById(Integer id) throws PostException;
    public List<Post> findAllPostByUserIds(List<Integer> userIds) throws PostException,UserException;
    public User savePost(Integer postId, Integer userId) throws PostException, UserException;
    public User unSavePost(Integer postId,Integer userId) throws PostException,UserException;
    public Post likePost(Integer postId, Integer userId) throws PostException,UserException;
    public Post unLikePost(Integer postId, Integer userId) throws PostException,UserException;





}
