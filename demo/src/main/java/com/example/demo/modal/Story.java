package com.example.demo.modal;

import com.example.demo.dto.UserDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "Stories")
public class Story {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="id", column=@Column(name="user_id")),
            @AttributeOverride(name="email", column=@Column(name="user_email"))
    })



    private UserDto user;
    @NotNull
    private String image;
    private String caption;
    private LocalDateTime timeStamp;

    public Story(Integer id, UserDto user, String image, String caption, LocalDateTime timeStamp) {
        this.id = id;
        this.user = user;
        this.image = image;
        this.caption = caption;
        this.timeStamp = timeStamp;
    }
    public Story(){

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

public String getImage() {
        return image;
        }

public void setImage(String image) {
        this.image = image;
        }

public String getCaption() {
        return caption;
        }

public void setCaption(String caption) {
        this.caption = caption;
        }

public LocalDateTime getTimeStamp() {
        return timeStamp;
        }

public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
        }
}
