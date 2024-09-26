package com.example.backback.controller;

import com.example.backback.domain.entity.User;
import com.example.backback.domain.entity.post.CommentPost;
import com.example.backback.domain.entity.post.LikeComment;
import com.example.backback.domain.entity.post.LikePost;
import com.example.backback.domain.entity.post.Post;
import com.example.backback.dto.response.ResponMessage;
import com.example.backback.security.userprincal.UserDetailService;
import com.example.backback.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/api/likepost")
@RestController
@CrossOrigin(origins = "*")
public class LikePostController {

    @Autowired
    UserDetailService userDetailService;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    PostServiceImpl postService;
    @Autowired
    LikeServiceImpl likeService;

    // tang like vao comment theo id cua bai post
    @PostMapping("/{id}")
    public ResponseEntity<?> likePlus(@PathVariable Long id) {
        // lay user hientai
        User user = userDetailService.getCurrentUser();

        // lay ra bai post day
        Optional<Post> post = postService.findById(id);
        if(!post.isPresent())
            return new ResponseEntity<>(new ResponMessage("khong tim thay bai post"),HttpStatus.BAD_REQUEST);

        // lay ra dc roi kiem tra xem nguoi do da like hay chua
        Optional<LikePost> likePost = likeService.findByUserAndPost(user,post.get());

        if(!likePost.isPresent()){
            // tao like
            LikePost like = new LikePost(user,post.get());
            // lay ra comment
            post.get().setLike_count(post.get().getLike_count()+1);

            likeService.save(like);
            postService.save(post.get());
            return new ResponseEntity<>(post.get().getLike_count(),HttpStatus.OK);
        }

        // neu ton tai thi xoa no di
        likeService.delete(likePost.get());
        if(post.get().getLike_count() > 0)
            post.get().setLike_count(post.get().getLike_count()-1);
        postService.save(post.get());
//        return new ResponseEntity<>(new ResponMessage("unlike done"),HttpStatus.OK);
        return new ResponseEntity<>(post.get().getLike_count(),HttpStatus.OK);
    }

    @GetMapping("/check/{id}")
    public ResponseEntity<?> checkLike(@PathVariable Long id){
        // lay user hientai
        User user = userDetailService.getCurrentUser();

        // lay ra bai post day
        Optional<Post> post = postService.findById(id);
        if(!post.isPresent())
            return new ResponseEntity<>(new ResponMessage("khong tim thay"),HttpStatus.BAD_REQUEST);

        Optional<LikePost> likePost = likeService.findByUserAndPost(user,post.get());
        if(!likePost.isPresent()) {
//            return new ResponseEntity(new ResponMessage("chua like"),HttpStatus.OK);
            return new ResponseEntity(post.get().getLike_count(),HttpStatus.OK);
        }

        return new ResponseEntity(post.get().getLike_count(),HttpStatus.BAD_REQUEST);
    }
}
