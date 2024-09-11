package com.revature.spoder_app.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * A test endpoint to verify that the application is running
     * @return A response entity with a message indicating that the application is running
     */
    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint() {
        return ResponseEntity.ok("Hello World");
    }

    /**
     * Posts a new user to the database
     * @param user The user to be added to the database
     * @return A response entity with the user that was added to the database
     */
    @PostMapping("/register")
    public ResponseEntity<Object> postNewUser(@RequestBody User user) {
        try {
            return ResponseEntity.status(201).body(userService.create(user));
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error: " + e.getMessage());
        }
    }

    /**
     * Retrieves all users from the database. Only users with the ADMIN role can access this endpoint.
     * @return A response entity with a list of all users in the database
     */
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers(@RequestHeader User.UserType userType) {
        if (userType != User.UserType.ADMIN) {
            return ResponseEntity.status(403).build();
        }
        List<User> users = userService.findAll();
        if (users.isEmpty()) {
            return ResponseEntity.status(204).body(users);
        }
        return ResponseEntity.ok(users);
    }

    /**
     * Retrieves a user by their id
     * @param id The id of the user to be retrieved
     * @return A response entity with the user that was retrieved
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<User> getUserById(@RequestHeader User.UserType userType, @RequestHeader int userId, @PathVariable int id) {
        if(userId != id && userType != User.UserType.ADMIN) {
            return ResponseEntity.status(403).build();
        }
        User user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(user);
    }

    /**
     * Retrieves a user by their id from the header
     * @param userId The id of the user to be retrieved
     * @return A response entity with the user that was retrieved
     */
    @GetMapping("/user")
    public ResponseEntity<Object> getUser(@RequestHeader int userId) {
        User user = userService.findById(userId);
        if (user == null) {
            return ResponseEntity.status(404).body("User not found");
        }
        return ResponseEntity.ok(user);
    }

    /**
     * Updates a user in the database
     * @param updatedUser The updated user
     * @return A response entity with the updated user
     */
    @PutMapping("/update")
    public ResponseEntity<Object> updateUser(@RequestHeader User.UserType userType, @RequestHeader int userId, @RequestBody User updatedUser) {
        updatedUser.setUserId(userId);
        updatedUser.setUserType(userType);
        try {
            return ResponseEntity.ok(userService.update(updatedUser));
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error: " + e.getMessage());
        }
    }

    /**
     * Deletes all users from the database. Only users with the ADMIN role can access this endpoint.
     * @param userType The role of the user making the request
     * @return A response entity with a message indicating the success or failure of the operation
     */
    @DeleteMapping("/all")
    public ResponseEntity<String> deleteAllUsers(@RequestHeader User.UserType userType) {
        if (userType != User.UserType.ADMIN) {
            return ResponseEntity.status(403).body("You do not have permission to delete users");
        }

        userService.deleteAll();
        return ResponseEntity.ok("All users deleted");
    }

    /**
     * Deletes a user by their id. Only users with the ADMIN role can access this endpoint.
     * @param id The id of the user to be deleted
     * @return A response entity with a message indicating the success or failure of the operation
     */
    @DeleteMapping("/id/{id}")
    public ResponseEntity<String> deleteUserById(@RequestHeader User.UserType userType, @RequestHeader int userId, @PathVariable int id) {
        if (userId != id) {
            if (userType != User.UserType.ADMIN) {
                return ResponseEntity.status(403).body("You do not have permission to delete this user");
            }
        }

        userService.delete(userService.findById(id));
        return ResponseEntity.ok("User deleted");
    }
}
