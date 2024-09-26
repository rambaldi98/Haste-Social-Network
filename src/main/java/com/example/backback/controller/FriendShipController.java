package com.example.backback.controller;

import com.example.backback.domain.entity.User;
import com.example.backback.domain.entity.friend.Friend;
import com.example.backback.dto.request.FriendRequestForm;
import com.example.backback.dto.response.ResponMessage;
import com.example.backback.security.userprincal.UserDetailService;
import com.example.backback.service.impl.FriendshipServiceImpl;
import com.example.backback.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequestMapping("/api/friend")
@RestController
@CrossOrigin(origins = "*")
public class FriendShipController {
    @Autowired
    FriendshipServiceImpl friendshipService;
    @Autowired
    UserDetailService userDetailService;
    @Autowired
    UserServiceImpl userService;


    @PostMapping("/addFriend")

    public ResponseEntity<?> addFriend(@RequestBody FriendRequestForm requestForm) {
        // check xem user name co ton tai trong db hay khong

        User userOne = userDetailService.getCurrentUser();

        Optional<User> userTwo = userService.findByUsername(requestForm.getUsernametwo());

        if(!userTwo.isPresent()) return new ResponseEntity<>(new ResponMessage("not find user"), HttpStatus.BAD_REQUEST);
        User user = userTwo.get();
        // check xem 2 thang kia co la ban hay khong
        Optional<Friend> friendCheck = friendshipService.findByUseroneAndAndUsertwo(userOne,user);
        if(friendCheck.isPresent()) return new ResponseEntity<>(new ResponMessage("not add friend"), HttpStatus.BAD_REQUEST);
        // ko phai tao moi
        Friend friend = new Friend(userOne,user);

        return new ResponseEntity<>(friendshipService.save(friend),HttpStatus.OK);

    }


    @PostMapping("/accept/{id}")
    public ResponseEntity<?> accessFriend(@PathVariable("id") Long id) {
        // lay lai friend

        Optional<Friend> friend = friendshipService.findById(id);
        if(!friend.isPresent()) return new ResponseEntity<>(new ResponMessage("not find"), HttpStatus.BAD_REQUEST);

        // kiem tra xem user hien tai co la user 2 hay khong trong db hay khong
        if(friend.get().getUsertwo().getUsername().equals(userDetailService.getCurrentUser().getUsername())) {
            if (friend.get().getStatus() == 0) {
                friend.get().setStatus(1);
                friendshipService.save(friend.get());
                return new ResponseEntity<>(new ResponMessage("ket ban thanh cong"), HttpStatus.OK);
            }

            if (friend.get().getStatus() == 1) return new ResponseEntity<>(new ResponMessage("da la ban"), HttpStatus.BAD_REQUEST);

        }

        return new ResponseEntity<>(new ResponMessage("khong the access"),HttpStatus.BAD_REQUEST);

    }

    // khong chap nhan ket ban

    @PostMapping("/cancel/{id}")
    public ResponseEntity<?> cancelFriend(@PathVariable("id") Long id){
        // lay ve friend

        Optional<Friend> friend = friendshipService.findById(id);
        if(!friend.isPresent()) return new ResponseEntity<>(new ResponMessage("not find"), HttpStatus.BAD_REQUEST);

        if(friend.get().getUsertwo().getUsername().equals(userDetailService.getCurrentUser().getUsername())){

//            if(friend.get())

            if(friend.get().getStatus() == 0 ||friend.get().getStatus() == 1  )  {
                friendshipService.delete(id);
                return new ResponseEntity<>(new ResponMessage("huy ket ban thanh cong"),HttpStatus.OK);
            }

            if(friend.get().getStatus() == 0 ||friend.get().getStatus() == 2  )  {
                return new ResponseEntity<>(new ResponMessage("chan ket ban khong the huy"),HttpStatus.BAD_REQUEST);
            }

//            friend.get().setStatus(1);
//            return new ResponseEntity<>(friendshipService.save(friend.get()),HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponMessage("khong the huy ket ban "),HttpStatus.BAD_REQUEST);
    }

    // chan ket ban id cua user can chan

    @PostMapping("/block/{id}")
    public ResponseEntity<?> blockFriend(@PathVariable("id") Long id){
        User userOne = userDetailService.getCurrentUser();
        // lay ve frend
        // chac gi no co trong ban ghi
        Optional<User> usertwo = userService.findUserById(id);
        if(!usertwo.isPresent())
                return new ResponseEntity<>(new ResponMessage("khong tim thay user"),HttpStatus.BAD_REQUEST);

        Optional<Friend> friendCheck = friendshipService.findByUseroneAndAndUsertwo(userOne,usertwo.get());
//        Optional<Friend> friend = friendshipService.findById(id);
        if(!friendCheck.isPresent()) {
            Friend friend = new Friend(userOne,usertwo.get());
            friend.setStatus(2);
            friendshipService.save(friend);
            return new ResponseEntity<>(new ResponMessage("chan thanh cong"),HttpStatus.OK);
            // chan dc
//            return new ResponseEntity<>(new ResponMessage("not found"), HttpStatus.BAD_REQUEST);
        }
        // neu da co roi thi
        friendCheck.get().setStatus(2);
        friendshipService.save(friendCheck.get());
        return new ResponseEntity<>(new ResponMessage("chan thanh cong"), HttpStatus.OK);
        // tim xem co ban ghi la ban hay gi khong

//        if(friend.get().getUsertwo().getUsername().equals(userDetailService.getCurrentUser().getUsername())){
// boi vi la usertwo moi la thang   cho xac nhan

//            if(friend.get())

//            if(friend.get().getStatus() == 0 ||friend.get().getStatus() == 1  )  {
//                friend.get().setStatus(2);
//                friendshipService.save(friend.get());
//                return new ResponseEntity<>(new ResponMessage("chan thanh cong"),HttpStatus.OK);
//            }
//
////        }
//        return new ResponseEntity<>(new ResponMessage("khong the chan ban "),HttpStatus.BAD_REQUEST);
    }


    //huy ket ban
    @PostMapping("/unfriend/{id}")
    public ResponseEntity<?> unFriend(@PathVariable("id") Long id){
        // lay ve frend

        Optional<Friend> friend = friendshipService.findById(id);
        if(!friend.isPresent()) return new ResponseEntity<>(new ResponMessage("not find"), HttpStatus.BAD_REQUEST);

//        if(!friend.get().getUsertwo().getUsername().equals(userDetailService.getCurrentUser().getUsername())){


            if(friend.get().getStatus() == 1  )  {
                friendshipService.delete(id);
                return new ResponseEntity<>(new ResponMessage("Xoa thanh cong"),HttpStatus.OK);
            }

//        }
        return new ResponseEntity<>(new ResponMessage("khong the xoa ban "),HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/cancelRequest/{id}")
    public ResponseEntity<?> cancelRequestSend(@PathVariable("id") Long id){
        // lay ve frend

        Optional<Friend> friend = friendshipService.findById(id);
        if(!friend.isPresent()) return new ResponseEntity<>(new ResponMessage("not find"), HttpStatus.BAD_REQUEST);

        if(!friend.get().getUsertwo().getUsername().equals(userDetailService.getCurrentUser().getUsername())){


            if(friend.get().getStatus() == 0 )  {
                friendshipService.delete(id);
                return new ResponseEntity<>(new ResponMessage("Xoa thanh cong"),HttpStatus.OK);
            }

        }
        return new ResponseEntity<>(new ResponMessage("khong the xoa ban "),HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/list")
    @Transactional
    public ResponseEntity<?> getFriend(){
        // lay user hien tai ra

        User currentUser = userDetailService.getCurrentUser();
        List fiends = friendshipService.findAllFriendByStatus(currentUser.getId(),1);
        if(fiends.isEmpty()){
            return new ResponseEntity<>(new ResponMessage("khong co ban"),HttpStatus.NOT_FOUND);
        }
        //
        List friendList = new ArrayList();

        for ( Object friend: fiends
             ) {
            // kiem tra xem userone co la user hien tai ko?
            Friend friendTest = (Friend) friend;
            if(!friendTest.getUserone().getUsername().equals(currentUser.getUsername())){
//                friendList.remove(friendTest);
                User user = friendTest.getUserone();
                 friendTest.setUserone(currentUser);
                 friendTest.setUsertwo(user);
                friendList.add( friendTest);
            } else
                friendList.add(friendTest);
        }

        return new ResponseEntity<>(friendList,HttpStatus.OK);

        // lay ra danh sach ban
//            List a = (List) friendshipService.findAllFriendByStatus(currentUser.getId(),1);
        // dau tien la tim userone =  user hien tai => usertwo
        // tim usertwo = user hien ttai => userone

        // hay doi usertwo  user hien tai userone

//        return new ResponseEntity<>(HttpStatus.OK);

    }

    //loi moi ket ban da gui
    @GetMapping("/friendrequestsent")
    public ResponseEntity<?> getFriendRequestSent(){
        // lay user hien tai ra

        User currentUser = userDetailService.getCurrentUser();
        // lay ra danh sach ban
//            List a = (List) friendshipService.findAllFriendByStatus(currentUser.getId(),1);
        return new ResponseEntity<>(friendshipService.findAllFriendByUserOneStatus(currentUser.getId(),0),HttpStatus.OK);
    }
    // lay ra danh sach can accept


    //cho ban xac nhan ket ban
    @GetMapping("/friendrequestreceived")
    public ResponseEntity<?> getFriendRequestReceived(){
        User currentUser = userDetailService.getCurrentUser();
       return new ResponseEntity<>(friendshipService.findAllFriendByUserTwoStatus(currentUser.getId(),0), HttpStatus.OK);
    }






}
