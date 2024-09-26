package com.example.backback.controller;

import com.example.backback.domain.entity.Role;
import com.example.backback.domain.entity.RoleName;
import com.example.backback.domain.entity.User;
import com.example.backback.dto.request.ChangeInformationForm;
import com.example.backback.dto.request.SignInForm;
import com.example.backback.dto.response.ResponMessage;
import com.example.backback.security.jwt.JwtProvider;
import com.example.backback.security.userprincal.UserPrinciple;
import com.example.backback.service.impl.RoleServiceImpl;
import com.example.backback.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RequestMapping("/api/admin")
@RestController
@CrossOrigin(origins = "*")
public class AdminController {

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


    ;
    @GetMapping
    public ResponseEntity<?> test(){
//        System.out.println( SecurityContextHolder.getContext().getAuthentication());
            UserPrinciple userPrinciple = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(userPrinciple.getPassword());
    }

    @PostMapping("/change/password")
    public  ResponseEntity<?> changePass(@Valid @RequestBody SignInForm signInForm) {
//        if()
//        SecurityContextHolder.getContext().getAuthentication().getPrincipal()

        UserPrinciple userPrinciple = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        System.out.println(userPrinciple + signInForm.getUsername());
        // tim kiem user trong data base

        if(signInForm.getPassword().length() < 6 | signInForm.getPassword().length() >32)
            return new ResponseEntity<>(new ResponMessage("password valid"), HttpStatus.BAD_REQUEST);
        if(signInForm.getUsername().equals(userPrinciple.getUsername()))
        {
//            return ResponseEntity.ok("co the doi mat khau");
            Optional<User> user = userService.findByUsername(signInForm.getUsername());
            user.get().setPassword(passwordEncoder.encode(signInForm.getPassword()));
            userService.save(user.get());
//            System.out.println(jwtProvider);
//          jwtProvider.createToken(authenticationManager.authenticate(SecurityContextHolder.getContext().getAuthentication()));
//            System.out.println(token);
            return  new ResponseEntity<> ( new ResponMessage("change password success"),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponMessage("user valid"), HttpStatus.BAD_REQUEST);
        }
//        return new ResponseEntity<>(new ResponMessage("not fount user"), HttpStatus.OK);
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
        user.setImage(informationForm.getImage());

//        System.out.println(user);
//
//                User user = new User(
//                        userPrinciple.getId(),
//                        userPrinciple.getUsername(),
//                        userPrinciple.getPassword(),
//                        informationForm.getEmail(),
//                        informationForm.getPhone(),
//                        informationForm.getBirthday(),
//                        informationForm.getCity(),
//                        informationForm.getImage()
//                );
//                 String roles = String.valueOf(userPrinciple.getRoles());
//        System.out.println(roles);

//                 Role role  = userPrinciple.getRoles();
//        System.out.println(userPrinciple.getRoles());
//                Role role = (Role) userPrinciple.getRoles();
//        System.out.println(role);
//        System.out.println(roles);
//                user.setRoles(roles);
//        System.out.println(userPrinciple.getRoles());
//                user.setRoles((Set<Role>) userPrinciple.getRoles());

                userService.save(user);

        return new ResponseEntity<>(user,HttpStatus.OK);
    }

}
