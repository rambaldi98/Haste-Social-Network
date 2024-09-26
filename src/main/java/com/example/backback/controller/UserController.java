package com.example.backback.controller;

import com.example.backback.domain.entity.Password;
import com.example.backback.domain.entity.User;
import com.example.backback.dto.request.ChangeInformationForm;
import com.example.backback.dto.request.FriendRequestForm;
import com.example.backback.dto.request.SignInForm;
import com.example.backback.dto.request.UserForm;
import com.example.backback.dto.response.ResponMessage;
import com.example.backback.security.jwt.JwtProvider;
import com.example.backback.security.userprincal.UserDetailService;
import com.example.backback.security.userprincal.UserPrinciple;
import com.example.backback.service.impl.RoleServiceImpl;
import com.example.backback.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequestMapping("/api/user")
@RestController
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    UserServiceImpl userService;
    @Autowired
    RoleServiceImpl roleService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    UserDetailService userDetailService;

    @GetMapping("")
    public ResponseEntity<?> test(){
        return ResponseEntity.ok("hello user" );
    }

        @PostMapping("/change/password")
    public ResponseEntity<ResponMessage> changPassword(@RequestBody Password password){
        User userCurrent = userDetailService.getCurrentUser();
        String message;
        if(userService.checkPassword(userCurrent, password.getCurrentPassword())){
            userCurrent.setPassword(passwordEncoder.encode(password.getNewPassword()));
            userService.save(userCurrent);
            message = "CHANGE PASSWORD SUCCESSFULLY !";
            System.out.println(password.getNewPassword());
            return new ResponseEntity<>(new ResponMessage(message), HttpStatus.OK);
        }else {
            System.out.println(password.getCurrentPassword());
            message = "CHANGE PASSWORD FAILED";
        }
        return ResponseEntity
                .badRequest()
                .body(new ResponMessage(message));
    }

    @PostMapping("/change/infor")
    public  ResponseEntity<?> changeInfor(@Valid @RequestBody ChangeInformationForm informationForm){
        UserPrinciple userPrinciple = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // lay user hien tai ra
        User user = userService.findByUsername(userPrinciple.getUsername()).get();

//        Optional<User> user = userService.findByUsername(userPrinciple.getUsername());

        user.setPhone(informationForm.getPhone());
        user.setBirthday(informationForm.getBirthday());
        user.setCity(informationForm.getCity());
//        user.setImage(informationForm.getImage());

        userService.save(user);

        return new ResponseEntity<>(user,HttpStatus.OK);
    }
    @GetMapping("/getuser")
    public ResponseEntity<User> getUser(){
        User userCurrent = userDetailService.getCurrentUser();
        System.out.println(userCurrent);
        return  new ResponseEntity<>(userCurrent,HttpStatus.OK);
    }

    @GetMapping("/allUser")
    public ResponseEntity<Iterable<User>> getAllUSer(){
        User currentUser = userDetailService.getCurrentUser();
        List users = (List) userService.getAllUser();
        users.remove(currentUser);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/allUserNoStatus")
    public ResponseEntity<Iterable<Object>> getAllUSerNoStatus(){
        User currentUser = userDetailService.getCurrentUser();
        List users = (List) userService.getAllUserAndStatus();
        users.remove(currentUser);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/change/avatar")
    public ResponseEntity<User> changeAvatar(@Valid @RequestBody ChangeInformationForm informationForm){
        UserPrinciple userPrinciple = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(userPrinciple.getUsername()).get();
        user.setImage(informationForm.getImage());
        userService.save(user);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @GetMapping("/viewInfor/{id}")
    public ResponseEntity<?> ViewInforFriend(@PathVariable Long id){
        return new ResponseEntity<>(userService.findUserById(id), HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<Iterable<User>> searchByUsername(@RequestBody FriendRequestForm userForm){
        return new ResponseEntity<>(userService.searchByUsername(userForm.getUsernametwo()), HttpStatus.OK);
    }

}
