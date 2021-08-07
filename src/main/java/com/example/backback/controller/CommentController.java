package com.example.backback.controller;

import com.example.backback.domain.entity.User;
import com.example.backback.domain.entity.post.CommentPost;
import com.example.backback.domain.entity.post.Post;
import com.example.backback.dto.request.CommentPostCreate;
import com.example.backback.dto.request.PostCreate;
import com.example.backback.dto.response.ResponMessage;
import com.example.backback.mapper.CommentMapper;
import com.example.backback.security.userprincal.UserDetailService;
import com.example.backback.service.impl.CommentPostServiceImpl;
import com.example.backback.service.impl.FriendshipServiceImpl;
import com.example.backback.service.impl.PostServiceImpl;
import com.example.backback.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RequestMapping("/api/comment")
@RestController
@CrossOrigin(origins = "*")
public class CommentController {
    @Autowired
    CommentPostServiceImpl commentPostService;
    @Autowired
    UserDetailService userDetailService;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    PostServiceImpl postService;
    // tao comment theo id bai post
    @PostMapping("/create/{id}")
    public ResponseEntity<?> createComment(@PathVariable("id") Long id, @RequestBody CommentPostCreate commentPostCreate){
        // lay user hien tai
        User user = userDetailService.getCurrentUser();
        // kiem tra xem id bai post co ton tai hay khong

        Optional<Post> post = postService.findById(id);
        if(!post.isPresent())
            return new ResponseEntity<>(new ResponMessage("khong tim thay bai post"),HttpStatus.NOT_FOUND);
        // neu tim thay thi :
        // tao comment post
        CommentPost commentPost = CommentMapper.build(commentPostCreate);
        commentPost.setUser(user);
        commentPost.setPost(post.get());
        commentPostService.save(commentPost);

        return new ResponseEntity<>(new ResponMessage("create comment done"), HttpStatus.OK);
    }
    // sua comment bai viet theo id cua comment nha
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateComment(@PathVariable("id") Long id, @RequestBody CommentPostCreate commentPostCreate){
        // lay user hien tai
        User user = userDetailService.getCurrentUser();

        // kiem tra comment co ton tai hay khong

        Optional<CommentPost> commentPost = commentPostService.findById(id);
        if(!commentPost.isPresent())
            return new ResponseEntity<>(new ResponMessage("khong tim thay comment "),HttpStatus.NOT_FOUND);
        // kiem tra xem co ton tai bai post k ?
        Optional<Post> post = postService.findById(commentPost.get().getPost().getId());
        if(!post.isPresent())
            return new ResponseEntity<>(new ResponMessage("khong tim thay bai post"),HttpStatus.NOT_FOUND);
        // kiem tra xem user hien tai co phai chu comment hay khong

        if(user.getUsername().equals(commentPost.get().getUser().getUsername())){
            // dung roi thi sua
            commentPost.get().setText(commentPostCreate.getText());
            commentPost.get().setTimeUpdate(Instant.now());
            commentPostService.save(commentPost.get());
            return new ResponseEntity<>(new ResponMessage("update comment done"),HttpStatus.OK);
        }

        return new ResponseEntity<>(new ResponMessage("khong co quyen sua"),HttpStatus.FORBIDDEN);
    }

    // xoa comment theo id cua comment
    // gom co chu post dc xoa va nguoi viet dc xoa
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable("id") Long id) {

        User user = userDetailService.getCurrentUser();

        Optional<CommentPost> commentPost = commentPostService.findById(id);
        if(!commentPost.isPresent())
            return new ResponseEntity<>(new ResponMessage("khong tim thay comment "),HttpStatus.NOT_FOUND);

        Optional<Post> post = postService.findById(commentPost.get().getPost().getId());
        if(!post.isPresent())
            return new ResponseEntity<>(new ResponMessage("khong tim thay bai post"),HttpStatus.NOT_FOUND);

        if( user.getUsername().equals(post.get().getUser().getUsername())
                ||user.getUsername().equals(commentPost.get().getUser().getUsername())){
            // dung roi thi xoa
            commentPostService.deleteById(id);
            return new ResponseEntity<>(new ResponMessage("delete comment done"),HttpStatus.OK);
        }

        return new ResponseEntity<>(new ResponMessage("khong co quyen xoa"),HttpStatus.FORBIDDEN);
    }
    // get coment theo id post
    // va theo theo user hien tai cua bai post
    @GetMapping()
    public ResponseEntity<?> getListComment(){
        User user = userDetailService.getCurrentUser();

        List listcomment = (List) commentPostService.getAllCommentByUser(user);
        if(listcomment.isEmpty()) return new ResponseEntity<>(new ResponMessage("khong co bai post"), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(listcomment,HttpStatus.OK);
    }
}
