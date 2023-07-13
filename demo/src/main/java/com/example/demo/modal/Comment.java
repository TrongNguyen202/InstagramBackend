package com.example.demo.modal;

import com.example.demo.dto.UserDto;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="id", column=@Column(name="user_id")),
            @AttributeOverride(name="email", column=@Column(name="user_email"))
    })
    private UserDto user;
    private String content;
    @Embedded
    @ElementCollection
    private Set<UserDto> likedByusers = new HashSet<UserDto>();
    private LocalDateTime createAt;


    public Comment(Integer id, UserDto user, String content, Set<UserDto> likedByusers, LocalDateTime createAt) {
        this.id = id;
        this.user = user;
        this.content = content;
        this.likedByusers = likedByusers;
        this.createAt = createAt;
    }
    public  Comment(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Set<UserDto> getLikedByusers() {
        return likedByusers;
    }

    public void setLikedByusers(Set<UserDto> likedByusers) {
        this.likedByusers = likedByusers;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }
}
