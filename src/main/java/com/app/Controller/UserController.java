package com.app.Controller;

import com.app.Exception.UserAlreadyExistsException;
import com.app.Model.User;
import com.app.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    //Endpoint to create new user
    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody User newUser) {
        try {
            User createdUser = userService.createUser(newUser);
            return ResponseEntity.ok("User with ID " + createdUser.getUserId() + " added successfully!");
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the user");
        }
    }

    //Endpoint to list all users
    @GetMapping("/list")
    public List<User> userList() {
        return userService.userList();
    }


    //Endpoint to delete user
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        String result = userService.deleteUser(userId);
        if (result.equals("User with User ID: " + userId + " as been deleted successfully!")) {
            return ResponseEntity.ok(result);
        }
        else {
            return ResponseEntity.badRequest().body("User with user ID: " + userId + " not found!");
        }
    }


    //Endpoint to update user
    @PostMapping("/update/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable Long userId, @RequestBody User existingUser) {
        String result = userService.updateUser(userId, existingUser);
        if (result.equals("User with ID: " + userId + " has been updated successfully!")) {
            return ResponseEntity.ok(result);
        }
        else {
            return ResponseEntity.badRequest().body("User with ID: " + userId + " not found!");
        }
    }


}
