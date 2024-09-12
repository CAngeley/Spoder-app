package com.revature.spoder_app.User;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTestSuite {

    @Mock
    private UserService mockUserService;

    @InjectMocks
    private UserController userController;

    HttpHeaders headersAdmin;
    HttpHeaders headersCustomer;
    User mockUserAdmin;
    User mockUserCustomer;
    String validUserAdminJson;
    String validUserCustomerJson;

    @BeforeEach
    public void setUp() {
        headersAdmin = new HttpHeaders();
        headersAdmin.add("userId", "1");
        headersAdmin.add("userType", User.UserType.ADMIN.toString());

        mockUserAdmin = new User();
        mockUserAdmin.setUserId(1);
        mockUserAdmin.setFirstName("John");
        mockUserAdmin.setLastName("Doe");
        mockUserAdmin.setEmail("john.doe@example.com");
        mockUserAdmin.setPassword("password123");
        mockUserAdmin.setUserType(User.UserType.ADMIN);

        validUserAdminJson = """
                {
                "firstName": "John",
                "lastName": "Doe",
                "email": "john.doe@example.com",
                "password": "password123",
                "userType": "ADMIN"
                }""";

        headersCustomer = new HttpHeaders();
        headersCustomer.add("userId", "2");
        headersCustomer.add("userType", User.UserType.CUSTOMER.toString());

        mockUserCustomer = new User();
        mockUserCustomer.setUserId(2);
        mockUserCustomer.setFirstName("Jane");
        mockUserCustomer.setLastName("Doe");
        mockUserCustomer.setEmail("jane.doe@example.com");
        mockUserCustomer.setPassword("password456");
        mockUserCustomer.setUserType(User.UserType.CUSTOMER);

        validUserCustomerJson = """
                {
                "firstName": "Jane",
                "lastName": "Doe",
                "email": "jane.doe@example.com",
                "password": "password456",
                "userType": "CUSTOMER"
                }""";
    }

    /**
     * Tests the postNewUser method in the UserController class
     * @throws JsonProcessingException
     */
    @Test
    public void testPostNewUser() throws JsonProcessingException {

        when(mockUserService.create(mockUserAdmin)).thenReturn(mockUserAdmin);

        ResponseEntity<Object> response = userController.postNewUser(mockUserAdmin);

        assertEquals(201, response.getStatusCode().value());
        System.out.println("Response Code: " + response.getStatusCode().value());
        assertEquals(mockUserAdmin, response.getBody());
        System.out.println("Response Body: " + response.getBody());
        verify(mockUserService).create(mockUserAdmin);
    }

    /**
     * Tests the postNewUser method in the UserController class with missing fields
     * @throws JsonProcessingException
     */
    @Test
    public void testPostNewUserMissingFields() throws JsonProcessingException {
        User incompleteUser = new User();
        incompleteUser.setFirstName(null); // Missing first name
        incompleteUser.setLastName("Doe");
        incompleteUser.setEmail("john.doe@example.com");
        incompleteUser.setPassword("password123");

        when(mockUserService.create(incompleteUser)).thenThrow(new IllegalArgumentException("User must contain a first name, last name, email, and password"));

        ResponseEntity<Object> response = userController.postNewUser(incompleteUser);

        assertEquals(400, response.getStatusCode().value());
        System.out.println("Response Code: " + response.getStatusCode().value());
        assertEquals("Error: User must contain a first name, last name, email, and password", response.getBody());
        System.out.println("Response Body: " + response.getBody());
        verify(mockUserService).create(incompleteUser);
    }

    @Test
    public void testPostNewUserDuplicateEmail() throws JsonProcessingException {
        User duplicateEmailUser = new User();
        duplicateEmailUser.setFirstName("John");
        duplicateEmailUser.setLastName("Doe");
        duplicateEmailUser.setEmail("jane.doe@example.com"); // Duplicate email
        duplicateEmailUser.setPassword("password123");

        when(mockUserService.create(duplicateEmailUser)).thenThrow(new IllegalArgumentException("User with email jane.doe@example.com already exists"));

        ResponseEntity<Object> response = userController.postNewUser(duplicateEmailUser);

        assertEquals(400, response.getStatusCode().value());
        System.out.println("Response Code: " + response.getStatusCode().value());
        assertEquals("Error: User with email jane.doe@example.com already exists", response.getBody());
        System.out.println("Response Body: " + response.getBody());
        verify(mockUserService).create(duplicateEmailUser);
    }

    /**
     * Tests the getAllUsers method in the UserController class
     */
    @Test
    public void testGetAllUsers() {
        when(mockUserService.findAll()).thenReturn(List.of(mockUserAdmin, mockUserCustomer));

        ResponseEntity<List<User>> response = userController.getAllUsers(User.UserType.ADMIN);

        assertEquals(200, response.getStatusCode().value());
        System.out.println("Response Code: " + response.getStatusCode().value());
        assertEquals(List.of(mockUserAdmin, mockUserCustomer), response.getBody());
        System.out.println("Response Body: " + response.getBody());
        verify(mockUserService).findAll();
    }

    /**
     * Tests the getAllUsers method in the UserController class with a non-admin user
     */
    @Test
    public void testGetAllUsersNotAdmin() {
        ResponseEntity<List<User>> response = userController.getAllUsers(User.UserType.CUSTOMER);

        assertEquals(403, response.getStatusCode().value());
        System.out.println("Response Code: " + response.getStatusCode().value());
        assertNull(response.getBody());
        System.out.println("Response Body: " + response.getBody());
    }

    /**
     * Tests the getAllUsers method in the UserController class with an empty list of users
     */
    @Test
    public void testGetAllUsersEmpty() {
        List<User> users = List.of();
        when(mockUserService.findAll()).thenReturn(users);

        ResponseEntity<List<User>> response = userController.getAllUsers(User.UserType.ADMIN);

        assertEquals(204, response.getStatusCode().value());
        System.out.println("Response Code: " + response.getStatusCode().value());
        assertEquals(users, response.getBody());
        System.out.println("Response Body: " + response.getBody());
        verify(mockUserService).findAll();
    }

    /**
     * Tests the getUserById method in the UserController class
     */
    @Test
    public void testGetUserById() {
        when(mockUserService.findById(1)).thenReturn(mockUserAdmin);

        ResponseEntity<User> response = userController.getUserById(User.UserType.CUSTOMER,1, 1);

        assertEquals(200, response.getStatusCode().value());
        System.out.println("Response Code: " + response.getStatusCode().value());
        assertEquals(mockUserAdmin, response.getBody());
        System.out.println("Response Body: " + response.getBody());
        verify(mockUserService).findById(1);
    }

    /**
     * Tests the getUserById method in the UserController class with a non-admin user
     */
    @Test
    public void testGetUserByIdNotAdmin() {
        ResponseEntity<User> response = userController.getUserById(User.UserType.CUSTOMER,2, 1);

        assertEquals(403, response.getStatusCode().value());
        System.out.println("Response Code: " + response.getStatusCode().value());
        assertNull(response.getBody());
        System.out.println("Response Body: " + response.getBody());
    }

    /**
     * Tests the getUserById method in the UserController class with a different user id
     */
    @Test
    public void testGetUserByIdNotMatchingUserId() {
        ResponseEntity<User> response = userController.getUserById(User.UserType.CUSTOMER,1, 2);

        assertEquals(403, response.getStatusCode().value());
        System.out.println("Response Code: " + response.getStatusCode().value());
        assertNull(response.getBody());
        System.out.println("Response Body: " + response.getBody());
    }

    /**
     * Tests the getUserById method in the UserController class and no user is found
     */
    @Test
    public void testGetUserByIdNotFound() {
        when(mockUserService.findById(3)).thenReturn(null);

        ResponseEntity<User> response = userController.getUserById(User.UserType.ADMIN,1, 3);

        assertEquals(404, response.getStatusCode().value());
        System.out.println("Response Code: " + response.getStatusCode().value());
        assertNull(response.getBody());
        System.out.println("Response Body: " + response.getBody());
        verify(mockUserService).findById(3);
    }

    /**
     * Tests the getUser method in the UserController class
     */
    @Test
    public void testGetUser() {
        when(mockUserService.findById(2)).thenReturn(mockUserCustomer);

        ResponseEntity<Object> response = userController.getUser(2);

        assertEquals(200, response.getStatusCode().value());
        System.out.println("Response Code: " + response.getStatusCode().value());
        assertEquals(mockUserCustomer, response.getBody());
        System.out.println("Response Body: " + response.getBody());
        verify(mockUserService).findById(2);
    }

    /**
     * Tests the getUser method in the UserController class when the user is not found
     */
    @Test
    public void testGetUserNotFound() {
        when(mockUserService.findById(3)).thenReturn(null);

        ResponseEntity<Object> response = userController.getUser(3);

        assertEquals(404, response.getStatusCode().value());
        System.out.println("Response Code: " + response.getStatusCode().value());
        assertEquals("User not found", response.getBody());
        System.out.println("Response Body: " + response.getBody());
        verify(mockUserService).findById(3);
    }

    /**
     * Tests the updateUser method in the UserController class
     * @throws JsonProcessingException
     */
    @Test
    public void testUpdateUser() throws JsonProcessingException {
        when(mockUserService.update(mockUserCustomer)).thenReturn(mockUserCustomer);

        ResponseEntity<Object> response = userController.updateUser(User.UserType.CUSTOMER,2, mockUserCustomer);

        assertEquals(200, response.getStatusCode().value());
        System.out.println("Response Code: " + response.getStatusCode().value());
        assertEquals(mockUserCustomer, response.getBody());
        System.out.println("Response Body: " + response.getBody());
        verify(mockUserService).update(mockUserCustomer);
    }

    /**
     * Tests the deleteAllUsers method in the UserController class
     */
    @Test
    public void testDeleteAllUsers() {
        ResponseEntity<String> response = userController.deleteAllUsers(User.UserType.ADMIN);

        assertEquals(200, response.getStatusCode().value());
        System.out.println("Response Code: " + response.getStatusCode().value());
        assertEquals("All users deleted", response.getBody());
        System.out.println("Response Body: " + response.getBody());
        verify(mockUserService).deleteAll();
    }

    /**
     * Tests the deleteAllUsers method in the UserController class with a non-admin user
     */
    @Test
    public void testDeleteAllUsersNotAdmin() {
        ResponseEntity<String> response = userController.deleteAllUsers(User.UserType.CUSTOMER);

        assertEquals(403, response.getStatusCode().value());
        System.out.println("Response Code: " + response.getStatusCode().value());
        assertEquals("You do not have permission to delete users", response.getBody());
        System.out.println("Response Body: " + response.getBody());
    }

    /**
     * Tests the deleteUserById method in the UserController class
     */
    @Test
    public void testDeleteUserById() {
        when(mockUserService.findById(2)).thenReturn(mockUserCustomer);

        ResponseEntity<String> response = userController.deleteUserById(User.UserType.CUSTOMER, 2, 2);

        assertEquals(200, response.getStatusCode().value());
        System.out.println("Response Code: " + response.getStatusCode().value());
        assertEquals("User deleted", response.getBody());
        System.out.println("Response Body: " + response.getBody());
        verify(mockUserService).delete(mockUserCustomer);
    }

    /**
     * Tests the deleteUserById method in the UserController class with an admin user
     */
    @Test
    public void testAdminCanDeleteDifferentUserId() {
        when(mockUserService.findById(2)).thenReturn(mockUserCustomer);

        ResponseEntity<String> response = userController.deleteUserById(User.UserType.ADMIN, 1, 2);

        assertEquals(200, response.getStatusCode().value());
        System.out.println("Response Code: " + response.getStatusCode().value());
        assertEquals("User deleted", response.getBody());
        System.out.println("Response Body: " + response.getBody());
        verify(mockUserService).delete(mockUserCustomer);
    }

    /**
     * Tests the deleteUserById method in the UserController class with a non-admin user
     */
    @Test
    public void testDeleteUserByIdNotAdmin() {
        ResponseEntity<String> response = userController.deleteUserById(User.UserType.CUSTOMER,2, 1);

        assertEquals(403, response.getStatusCode().value());
        System.out.println("Response Code: " + response.getStatusCode().value());
        assertEquals("You do not have permission to delete this user", response.getBody());
        System.out.println("Response Body: " + response.getBody());
    }

    /**
     * Tests the deleteUserById method in the UserController class with a different user id
     */
    @Test
    public void testDeleteUserByIdDifferentUserId() {
        ResponseEntity<String> response = userController.deleteUserById(User.UserType.CUSTOMER,1, 2);

        assertEquals(403, response.getStatusCode().value());
        System.out.println("Response Code: " + response.getStatusCode().value());
        assertEquals("You do not have permission to delete this user", response.getBody());
        System.out.println("Response Body: " + response.getBody());
    }
}