package com.revature.spoder_app.User;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.revature.spoder_app.util.interfaces.Serviceable;
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

    @Override
    public User create(User newUser) throws JsonProcessingException {
        if(newUser.getEmail() == null || newUser.getPassword() == null) {
            throw new IllegalArgumentException("User must contain an email and password");
        }

        if(newUser.getUserType() == null) {
            newUser.setUserType(User.UserType.CUSTOMER);
        }

        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        return userRepository.saveAndFlush(newUser);
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User update(User updatedObject) throws JsonProcessingException {
        return null;
    }

    @Override
    public Boolean delete(User deletedObject) {
        return null;
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
