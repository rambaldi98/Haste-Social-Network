package com.example.backback.controller;

import com.example.backback.domain.entity.User;
import com.example.backback.domain.entity.post.CommentPost;
import com.example.backback.domain.entity.post.Post;
import com.example.backback.dto.request.PostCreate;
import com.example.backback.dto.response.ResponMessage;
import com.example.backback.mapper.PostMapper;
import com.example.backback.security.userprincal.UserDetailService;
import com.example.backback.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/api/post")
@RestController
@CrossOrigin(origins = "*")
public class PostController {
    @Autowired
    PostServiceImpl postService;
    @Autowired
    LikeServiceImpl likeService;
    @Autowired
    UserDetailService userDetailService;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    CommentPostServiceImpl commentPostService;
    @Autowired
    LikeCommentServiceImpl likeCommentService;
    @PostMapping("/create")
    public ResponseEntity<?> createPost(@RequestBody PostCreate postCreate){

        Post post = PostMapper.build(postCreate);
        if(post == null) return new ResponseEntity<>(new ResponMessage("khong hop le"), HttpStatus.BAD_REQUEST);
        User currentUser =   userDetailService.getCurrentUser();
        if(currentUser == null) return new ResponseEntity<>(new ResponMessage("khong co quyen"),HttpStatus.ACCEPTED);
        post.setUser(currentUser);
        postService.save(post);
        return new ResponseEntity(new ResponMessage("create post done"), HttpStatus.OK);
    }
    // ham nay cho admin dung .
    @GetMapping("/getAllpost")
    public ResponseEntity<?> getAllPost(){

        return new ResponseEntity<>(postService.findAll(), HttpStatus.OK);
    }

    // lay tat ca post cua user hien tai ///
    @GetMapping("/getpost")
    public ResponseEntity<?> getPostByUsername(){
        // lay ra user hien tai
      User userPost = userDetailService.getCurrentUser();

        // tim trong sv
        List postList =  postService.findByUser(userPost);
        if(postList.isEmpty())
            return new ResponseEntity<>(new ResponMessage("khong co bai post nao"),HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(postList, HttpStatus.OK);
    }




    // lay psot theo id so sai
    @GetMapping("getPost/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Long id){
        Optional<Post> post = postService.findById(id);
        if(!post.isPresent())
            return new ResponseEntity<>(new ResponMessage("khong tim thay bai post"),HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    // sua bai post theo id va nguoi dung
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatePostById(@PathVariable Long id, @RequestBody PostCreate postCreate){
        // kiem tra user hien tai
        User user =  userDetailService.getCurrentUser();
        if(user == null)
            return new ResponseEntity<>(new ResponMessage(" khong co quyen"), HttpStatus.FORBIDDEN);
        // tim kiem bai viet
        Optional<Post> post =  postService.findById(id);
        if(!post.isPresent())
            return new ResponseEntity<>(new ResponMessage("khong tim thay bai post"),HttpStatus.BAD_REQUEST);
        // kiem tra user hien tai co phai chu post
        if(!user.getUsername().equals(postService.getUsernameById(id))){
            return new ResponseEntity<>(new ResponMessage(" khong co quyen"), HttpStatus.FORBIDDEN);
        }

        // sua post
        Post postupdate = PostMapper.buildUpdate(postCreate);
        post.get().setDescription(postupdate.getDescription());
        post.get().setStatus(postupdate.getStatus());
        post.get().setImage(postupdate.getImage());
        post.get().setTimeUpdate(postupdate.getTimeUpdate());
        // save post
        postService.save(post.get());
        return new ResponseEntity<>(new ResponMessage("update post done"),HttpStatus.OK);

    }
    // set role cho bai post
//    @PutMapping("change/status/{status}")
//    public ResponseEntity<?> changeStatus(@PathVariable)
    // lay 1 list theo user cai nay chua dung toi vi chua co role theo status chua xu ly
    @GetMapping("/getpost/{user}")
    public ResponseEntity<?> getPostByUsername(@PathVariable String user){
        // lay ra user
        Optional<User> userPost = userService.findByUsername(user);
        if(!userPost.isPresent())
            return new ResponseEntity<>(new ResponMessage("khong tim thay "),HttpStatus.BAD_REQUEST);


        List postList =  postService.findByUser(userPost.get());
        if(postList.isEmpty())
            return new ResponseEntity<>(new ResponMessage("khong co bai post nao"),HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(postList, HttpStatus.OK);
    }

    // xoa post theo id va  phai trung vs user hien tai
    // thieu xoa ca like va comment nua nen chua xong dc

    // xoa like post
    // xoa like comment
    // xoa comment
    @DeleteMapping("/remove/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {

        if(userDetailService.getCurrentUser() == null)
            return new ResponseEntity<>(new ResponMessage(" khong co quyen"), HttpStatus.BAD_REQUEST);
        Optional<Post> post = postService.findById(id);
        if(!post.isPresent())
            return new ResponseEntity<>(new ResponMessage("khong tim thay bai post"),HttpStatus.BAD_REQUEST);
        // kiem tra co phai user dang bai hay k
        if(userDetailService.getCurrentUser().getUsername().equals( postService.getUsernameById(id) )){

            likeService.deleteAllByPost(post.get());
            // lay list comment
            List comment = (List) commentPostService.getAllCommentByPost(post.get());
            for ( Object commentPost : comment
                 ) {
                likeCommentService.deleteAllByCommentPost((CommentPost) commentPost);
                commentPostService.delete((CommentPost) commentPost);
            }



            postService.remove(id);
            return new ResponseEntity<>(new ResponMessage("delete done"),HttpStatus.OK);
        }

        return new ResponseEntity<>(new ResponMessage("khong co quyen"), HttpStatus.FORBIDDEN);

    }

}
