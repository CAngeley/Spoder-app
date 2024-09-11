package com.revature.spoder_app.User;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.revature.spoder_app.util.interfaces.Serviceable;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements Serviceable<User> {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Creates a new user in the database
     *  User must contain a first name, last name, email, and password
     *  User email must not already exist in the database
     *  User type defaults to CUSTOMER if not provided
     *  Password is encoded before being saved to the database
     *  User is saved to the database
     * @param newUser The user to be created
     * @return The user that was created
     * @throws JsonProcessingException
     */
    @Override
    @Transactional
    public User create(User newUser) throws JsonProcessingException {
        if (newUser.getFirstName() == null || newUser.getFirstName().isEmpty() ||
                newUser.getLastName() == null || newUser.getLastName().isEmpty() ||
                newUser.getEmail() == null || newUser.getEmail().isEmpty() ||
                newUser.getPassword() == null || newUser.getPassword().isEmpty()) {
            throw new IllegalArgumentException("User must contain a first name, last name, email, and password");
        }

        if (userRepository.findByEmail(newUser.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User with email " + newUser.getEmail() + " already exists");
        }

        if(newUser.getUserType() == null) {
            newUser.setUserType(User.UserType.CUSTOMER);
        }

        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        try {
            return userRepository.saveAndFlush(newUser);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed database operation: " + e.getMessage());
        }
    }

    /**
     * Finds a user in the database by their ID
     * @param id The ID of the user to find
     * @return The user with the given ID
     */
    @Override
    public User findById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    /**
     * Updates a user in the database
     *  User must contain a first name, last name, email, and password
     *  User type defaults to CUSTOMER if not provided
     *  User must already exist in the database
     *  Password is encoded before being saved to the database
     *  User is saved to the database
     * @param updatedUser The user to be updated
     * @return The user that was updated
     * @throws JsonProcessingException
     */
    @Override
    public User update(User updatedUser) throws JsonProcessingException {
        if (updatedUser.getFirstName() == null || updatedUser.getFirstName().isEmpty() ||
                updatedUser.getLastName() == null || updatedUser.getLastName().isEmpty() ||
                updatedUser.getEmail() == null || updatedUser.getEmail().isEmpty() ||
                updatedUser.getPassword() == null || updatedUser.getPassword().isEmpty()) {
            throw new IllegalArgumentException("User must contain a first name, last name, email, and password");
        }

        if(updatedUser.getUserType() == null) {
            updatedUser.setUserType(User.UserType.CUSTOMER);
        }

        if (userRepository.findById(updatedUser.getUserId()).isPresent()) {
            updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            try {
                return userRepository.saveAndFlush(updatedUser);
            } catch (Exception e) {
                throw new IllegalArgumentException("Failed database operation: " + e.getMessage());
            }
        }
        return null;
    }

    /**
     * Deletes a user from the database
     *  User must already exist in the database
     * @param deletedUser The user to be deleted
     * @return True if the user was deleted, false if the user was not found
     */
    @Override
    public Boolean delete(User deletedUser) {
        if (userRepository.findById(deletedUser.getUserId()).isPresent()) {
            userRepository.delete(deletedUser);
            return true;
        }
        return false;
    }

    /**
     * Deletes all users from the database
     */
    public void deleteAll() {
        userRepository.deleteAll();
    }

    /**
     * Finds a user in the database by their email
     * @param email The email of the user to find
     * @return The user with the given email
     */
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}
