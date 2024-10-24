package com.app.Service;

import com.app.Exception.UserAlreadyExistsException;
import com.app.Model.User;
import com.app.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    //Method to add a new user
    public User createUser(User user) throws UserAlreadyExistsException {
        // Check if username already exists
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("User with Username: " + user.getUsername() + " already exists!");
        }

        // Check if User ID already exists
        if (userRepository.findByUserId(user.getUserId()).isPresent()) {
            throw new UserAlreadyExistsException("User with User ID: " + user.getUserId() + " already exists!");
        }

        // Check if email already exists
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with email: " + user.getEmail() + " already exists!");
        }

        // Add user if it does not exist
        return userRepository.save(user);
    }




    //Method to List all users
    public List<User> userList() {
        return userRepository.findAll();
    }


    //Method to Delete User
    public String deleteUser(Long userId) {
        //Get the user by their ID
        Optional<User> user = userRepository.findByUserId(userId);
        //Check if the User is present
        if (user.isPresent()) {
            userRepository.deleteById(userId);
            return ("User with User ID: " + userId + " as been deleted successfully!");
        }
        else {
            return ("User with user ID: " + userId + " not found!");
        }
    }


    //Method to update user
    public String updateUser(Long userId, User updatedUser) {
        //Find user by ID
        Optional<User> existingUserOptional = userRepository.findByUserId(userId);

        //Check if user exists
        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();
            existingUser.setFullName(updatedUser.getFullName());
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setDob(updatedUser.getDob());
            existingUser.setAddress(updatedUser.getAddress());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setOccupation(updatedUser.getOccupation());
            existingUser.setRole(updatedUser.getRole());
            existingUser.setPassword((updatedUser.getPassword()));
            existingUser.setDateUpdated(LocalDateTime.now());

            //Save updated details
            userRepository.save(existingUser);

            return ("User with ID: " + userId + " has been updated successfully!");
        }
        else {
            return ("User with ID: " + userId + " not found!");
        }
    }

}
