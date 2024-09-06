package com.revature.spoder_app.User;

import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<User> postNewUser(@RequestBody User user) {
        try {
            return ResponseEntity.status(201).body(userService.create(user));
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(400).build();
        }
    }

    /**
     * Retrieves all users from the database. Only users with the ADMIN role can access this endpoint.
     * @return A response entity with a list of all users in the database
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(@RequestHeader User.UserType userType) {
        if (userType != User.UserType.ADMIN) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(userService.findAll());
    }

    /**
     * Deletes all users from the database. Only users with the ADMIN role can access this endpoint.
     * @param userType The role of the user making the request
     * @return A response entity with a message indicating the success or failure of the operation
     */
    @DeleteMapping("/all")
    public ResponseEntity<String> deleteAllUsers(@RequestHeader User.UserType userType) {
        if (userType != User.UserType.ADMIN) {
            return ResponseEntity.status(403).body("You do not have permission to delete all users");
        }

        userService.deleteAll();
        return ResponseEntity.ok("All users deleted");
    }

}
