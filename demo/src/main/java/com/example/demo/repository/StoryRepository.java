package com.example.demo.repository;

import com.example.demo.modal.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoryRepository extends JpaRepository<Story,Integer> {
    @Query("select s from Story s where s.user.id= :userId")
    public List<Story> findAllStoryByUserId(@Param("userId") Integer userId);

}
