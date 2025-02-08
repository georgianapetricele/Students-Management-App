package org.example.mpp_backend.controller;


import lombok.AllArgsConstructor;
import org.example.mpp_backend.model.User;
import org.example.mpp_backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private UserService userService;

    @GetMapping
    public ResponseEntity<Collection<User>> getAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("/page/{currentPage}")
    public ResponseEntity<List<User>> getAllUsersPaginated(@PathVariable int currentPage) {
        return ResponseEntity.ok(userService.getAllUsersPaginated(currentPage));

    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    /*@GetMapping("/students/{id}")
    public ResponseEntity<Collection<Student>> getAllStudentsForUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findAllStudentsForUser(id));
    }*/

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.addUser(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}

