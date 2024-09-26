package com.example.backback.domain.entity.post;

import com.example.backback.domain.entity.User;
import com.sun.istack.Nullable;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.Instant;
import java.util.Collection;



@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Nullable
    @Lob
    private String description;

    private Integer status = 2;// private = 0,1 ,2 2 : public

    private String image;
    private String mp3Url;

    public String getMp3Url() {
        return mp3Url;
    }

    public void setMp3Url(String mp3Url) {
        this.mp3Url = mp3Url;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User user;


    private Instant createDate; // thoi gian post sua update thoi gian
    private Instant timeUpdate; // thoi gian update lai bai viet
    private Integer like_count = 0;
    private Integer comment_count = 0;



    public Post() {
    }
    // post create
    public Post(String description, Integer status, String image, Instant createDate) {
        this.description = description;
        this.status = status;
        this.image = image;
        this.createDate = createDate;
    }
    // update
    public Post(String description, Integer status, String image,  Instant timeUpdate, String a) {
        this.description = description;
        this.status = status;
        this.image = image;
        this.timeUpdate = timeUpdate;
    }

    public Post(String description, Integer status, String image, User user, Instant createDate) {
        this.description = description;
        this.status = status;
        this.image = image;
        this.user = user;
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public Instant getTimeUpdate() {
        return timeUpdate;
    }

    public void setTimeUpdate(Instant timeUpdate) {
        this.timeUpdate = timeUpdate;
    }

    public Integer getLike_count() {
        return like_count;
    }

    public void setLike_count(Integer like_count) {
        this.like_count = like_count;
    }

    public Integer getComment_count() {
        return comment_count;
    }

    public void setComment_count(Integer comment_count) {
        this.comment_count = comment_count;
    }
}
