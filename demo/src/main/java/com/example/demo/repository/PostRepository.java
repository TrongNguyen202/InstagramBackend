package com.example.demo.repository;

import com.example.demo.modal.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Integer> {
    @Query("SELECT p FROM Post p WHERE p.user.id = ?1")
    public List<Post> findByUserId(Integer userId);
    @Query("SELECT p FROM Post p WHERE p.user.id IN :users ORDER BY p.createAt DESC")
    public List<Post> findAllPostByUserIds(@Param("users") List<Integer> userIds);


}
