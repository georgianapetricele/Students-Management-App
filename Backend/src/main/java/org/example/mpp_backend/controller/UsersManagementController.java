package org.example.mpp_backend.controller;

import jakarta.servlet.http.HttpSession;
import org.example.mpp_backend.dto.RequestRes;
import org.example.mpp_backend.model.UserAccount;
import org.example.mpp_backend.service.UsersManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
@CrossOrigin(origins = "*")
public class UsersManagementController {

    @Autowired
    private UsersManagementService usersManagementService;

    @PostMapping("/auth/register")
    public ResponseEntity<RequestRes> register(@RequestBody RequestRes reg){

        return ResponseEntity.ok(usersManagementService.register(reg));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<RequestRes> login(@RequestBody RequestRes req, HttpSession session){
        // Store user information in session
        session.setAttribute("user", req.getUserAccount());

        return ResponseEntity.ok(usersManagementService.login(req));
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<RequestRes> refreshToken(@RequestBody RequestRes req){
        return ResponseEntity.ok(usersManagementService.refreshToken(req));
    }

    @GetMapping("/admin/get-all-users")
    public ResponseEntity<RequestRes> getAllUserAccounts(){
        return ResponseEntity.ok(usersManagementService.getAllUserAccounts());

    }

    @GetMapping("/admin/get-users/{userId}")
    public ResponseEntity<RequestRes> getUserByID(@PathVariable Integer userId){
        return ResponseEntity.ok(usersManagementService.getUsersById(userId));

    }

    @PutMapping("/admin/update/{userId}")
    public ResponseEntity<RequestRes> updateUser(@PathVariable Integer userId, @RequestBody UserAccount reqres){
        return ResponseEntity.ok(usersManagementService.updateUser(userId, reqres));
    }

    @GetMapping("/adminuser/get-profile")
    public ResponseEntity<RequestRes> getMyProfile(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        RequestRes response = usersManagementService.getMyInfo(email);
        return  ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/admin/delete/{userId}")
    public ResponseEntity<RequestRes> deleteUSer(@PathVariable Integer userId){
        return ResponseEntity.ok(usersManagementService.deleteUserById(userId));
    }

}
