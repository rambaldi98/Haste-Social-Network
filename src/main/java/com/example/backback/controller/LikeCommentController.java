package com.example.backback.controller;

import com.example.backback.domain.entity.User;
import com.example.backback.domain.entity.post.CommentPost;
import com.example.backback.domain.entity.post.LikeComment;
import com.example.backback.dto.response.ResponMessage;
import com.example.backback.security.userprincal.UserDetailService;
import com.example.backback.service.impl.CommentPostServiceImpl;
import com.example.backback.service.impl.LikeCommentServiceImpl;
import com.example.backback.service.impl.PostServiceImpl;
import com.example.backback.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/api/likecomment")
@RestController
@CrossOrigin(origins = "*")
public class LikeCommentController {
    @Autowired
    CommentPostServiceImpl commentPostService;
    @Autowired
    UserDetailService userDetailService;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    PostServiceImpl postService;
    @Autowired
    LikeCommentServiceImpl likeCommentService;

    // tang like vao comment theo id cua comment
    @PostMapping("/{id}")
    public ResponseEntity<?> likePlus(@PathVariable Long id) {
        // lay user hientai
        User user = userDetailService.getCurrentUser();

        // lay ra bai comment day
        Optional<CommentPost> commentPost = commentPostService.findById(id);
        if(!commentPost.isPresent())
            return new ResponseEntity<>(new ResponMessage("khong tim thay comment de like"), HttpStatus.NOT_FOUND);
        // lay ra dc roi kiem tra xem nguoi do da like hay chua
        Optional<LikeComment> likeComment = likeCommentService.findByUserAndComment(user,commentPost.get());
        if(!likeComment.isPresent()){
            // tao like
            LikeComment like = new LikeComment(user,commentPost.get());
            // lay ra comment
            commentPost.get().setLike_comment_count(commentPost.get().getLike_comment_count()+1);
            likeCommentService.save(like);
            commentPostService.save(commentPost.get());
            return new ResponseEntity<>(commentPost.get().getLike_comment_count(),HttpStatus.OK);
        }
        // neu ton tai thi xoa no di
        likeCommentService.delete(likeComment.get());
        if(commentPost.get().getLike_comment_count() > 0)
        commentPost.get().setLike_comment_count(commentPost.get().getLike_comment_count()-1);
        commentPostService.save(commentPost.get());
        return new ResponseEntity<>(commentPost.get().getLike_comment_count(),HttpStatus.OK);
    }

    @GetMapping("/check/{id}")
    public ResponseEntity<?> checkLike(@PathVariable Long id){
        User user = userDetailService.getCurrentUser();
        Optional<CommentPost> commentPost = commentPostService.findById(id);
        if(!commentPost.isPresent())
            return new ResponseEntity<>(new ResponMessage("khong tim thay comment de like"), HttpStatus.NOT_FOUND);
        Optional<LikeComment> likeComment = likeCommentService.findByUserAndComment(user,commentPost.get());
        if(!likeComment.isPresent()){
            return new ResponseEntity<>(commentPost.get().getLike_comment_count(),HttpStatus.OK);
        }
        return new ResponseEntity<>(commentPost.get().getLike_comment_count(),HttpStatus.BAD_REQUEST);
    }
}
