package com.example.Database_First.Controller;

import com.example.Database_First.Model.UserEntity;
import com.example.Database_First.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity user) {
        UserEntity createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
    // Authenticate user
    @PostMapping("/authenticate")
    public ResponseEntity<UserEntity> authenticateUser(@RequestBody UserEntity user) {
        Optional<UserEntity> authenticatedUser = userService.authenticateUser(user.getUserName(), user.getPassword());
        return authenticatedUser.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
    }


    // Get all users
//    @GetMapping
//    public ResponseEntity<List<UserEntity>> getAllUsers() {
//        List<UserEntity> users = userService.getAllUsers();
//        return new ResponseEntity<>(users, HttpStatus.OK);
//    }
//
//    // Get a specific user by ID
//    @GetMapping("/{id}")
//    public ResponseEntity<UserEntity> getUserById(@PathVariable Long id) {
//        Optional<UserEntity> user = userService.getUserById(id);
//        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
//                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }

    // Create a new user


    // Update an existing user
//    @PutMapping("/{id}")
//    public ResponseEntity<UserEntity> updateUser(@PathVariable Long id, @RequestBody UserEntity updatedUser) {
//        Optional<UserEntity> user = userService.updateUser(id, updatedUser);
//        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
//                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }

    // Delete a user by ID
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
//        boolean deleted = userService.deleteUser(id);
//        return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
//                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
}
