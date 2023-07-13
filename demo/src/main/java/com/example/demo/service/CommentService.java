package com.example.demo.service;

import com.example.demo.exceptions.CommentException;
import com.example.demo.exceptions.PostException;
import com.example.demo.exceptions.UserException;
import com.example.demo.modal.Comment;

public interface CommentService {
    public Comment createComment(Comment comment, Integer postId, Integer userId) throws UserException, PostException;
    public Comment findCommentById(Integer commentId) throws CommentException;
    public Comment likeComment(Integer commentId,Integer userId) throws CommentException, UserException;
    public Comment unLikeComment(Integer commentId,Integer userId) throws CommentException, UserException;



}
