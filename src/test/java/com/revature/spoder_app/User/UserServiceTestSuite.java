package com.revature.spoder_app.User;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTestSuite {
    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService mockUserService;

    User mockUser;

    @BeforeEach
    public void setUp() {
        mockUser = new User();
        mockUser.setUserId(1);
        mockUser.setFirstName("Emily");
        mockUser.setLastName("Davis");
        mockUser.setEmail("emily.davis@example.com");
        mockUser.setPassword("password123");
        mockUser.setUserType(User.UserType.CUSTOMER);
    }

    /**
     * This test case tests the create method of the UserService class
     * The method should return the user that was created
     * The user should have an encoded password
     * @throws JsonProcessingException
     */
    @Test
    public void testCreateUser() throws JsonProcessingException {
        System.out.println("Mock user: " + mockUser.toString());
        when(passwordEncoder.encode(mockUser.getPassword())).thenReturn("encodedPassword123");
        when(mockUserRepository.saveAndFlush(mockUser)).thenReturn(mockUser);
        User createdUser = mockUserService.create(mockUser);
        assertEquals(mockUser, createdUser);
        System.out.println("User created: " + createdUser.toString());
    }

    /**
     * This test case tests the findAll method of the UserService class
     * The method should return a list of all users in the database
     */
    @Test
    public void testFindAll() {
        List<User> mockUsers = List.of(mockUser, mockUser);
        when(mockUserRepository.findAll()).thenReturn(mockUsers);

        List<User> foundUsers = mockUserService.findAll();

        assertEquals(mockUsers.size(), foundUsers.size());
        assertEquals(mockUsers, foundUsers);
        verify(mockUserRepository).findAll();
        System.out.println("Users: " + foundUsers);
    }

    /**
     * This test case tests the findById method of the UserService class
     * The method should return the user with the given id
     */
    @Test
    public void testFindById() {
        when(mockUserRepository.findById(mockUser.getUserId())).thenReturn(java.util.Optional.of(mockUser));
        
        User user = mockUserService.findById(mockUser.getUserId());
        
        assertEquals(mockUser, user);
        verify(mockUserRepository).findById(mockUser.getUserId());
        System.out.println("User found: " + user.toString());
    }

    /**
     * This test case tests the update method of the UserService class
     * The method should return the updated user
     * The updated user should have an encoded password
     * @throws JsonProcessingException
     */
    @Test
    public void testUpdateUser() throws JsonProcessingException {
        User updatedUser = mockUser;
        updatedUser.setPassword("newPassword123");
        System.out.println("Original user: " + mockUser);
        when(mockUserRepository.findById(updatedUser.getUserId())).thenReturn(java.util.Optional.of(mockUser));
        when(passwordEncoder.encode(updatedUser.getPassword())).thenReturn("encodedNewPassword123");
        when(mockUserRepository.saveAndFlush(updatedUser)).thenReturn(updatedUser);
        User user = mockUserService.update(updatedUser);
        assertEquals(updatedUser, user);
        System.out.println("User updated: " + user);
    }

    /**
     * This test case tests the delete method of the UserService class
     * The method should return true if the user was deleted
     */
    @Test
    public void testDeleteUser() {
        when(mockUserRepository.findById(mockUser.getUserId())).thenReturn(java.util.Optional.of(mockUser));
        Boolean deleted = mockUserService.delete(mockUser);
        assertEquals(true, deleted);
        System.out.println("User id deleted: " + mockUser.getUserId());
    }

    /**
     * This test case tests the deleteAll method of the UserService class
     * The method should return true if all users were deleted
     */
    @Test
    public void testDeleteAllUsers() {
        mockUserService.deleteAll();
        verify(mockUserRepository).deleteAll();
        System.out.println("All users deleted");
    }


}
