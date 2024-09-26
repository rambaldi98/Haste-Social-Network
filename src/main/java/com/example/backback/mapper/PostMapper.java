package com.example.backback.mapper;

import com.example.backback.domain.entity.post.LikePost;
import com.example.backback.domain.entity.post.Post;
import com.example.backback.dto.request.PostCreate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostMapper {
    private Long id;
    private String description;
    private Integer status;
    private String image;
    private String mp3url;
    private Instant createDate;
    private Instant timeUpdate; // thoi gian update lai bai viet
    public PostMapper(String description, Integer status, String image,String mp3url, Instant createDate) {
        this.description = description;
        this.status = status;
        this.image = image;
        this.mp3url = mp3url;
        this.createDate = createDate;
    }

    public PostMapper(int id,String description, Integer status, String image,String mp3url,Instant timeUpdate) {
        this.description = description;
        this.status = status;
        this.image = image;
        this.mp3url = mp3url;
        this.timeUpdate = timeUpdate;
    }

    public static Post build(PostCreate postCreate){

    return new Post(
        postCreate.getDescription(),
            postCreate.getStatus(),
            postCreate.getImage(),
            Instant.now());
    }

    public static Post buildUpdate(PostCreate postCreate){

        return new Post(
                postCreate.getDescription(),
                postCreate.getStatus(),
                postCreate.getImage(),
                Instant.now(),
                "update");
    }

}
