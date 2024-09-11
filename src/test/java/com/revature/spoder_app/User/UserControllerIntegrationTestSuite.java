package com.revature.spoder_app.User;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.http.HttpHeaders;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    HttpHeaders headersAdmin;
    HttpHeaders headersCustomer;
    User mockUserAdmin;
    User mockUserCustomer;
    String validUserAdminJson;
    String validUserCustomerJson;

    /**
     * This method is run before each test case to set up the mocks and headers
     */
    @BeforeEach
    public void setUp() {
        headersAdmin = new HttpHeaders();
        headersAdmin.add("userId", "1");
        headersAdmin.add("userType", User.UserType.ADMIN.toString());

        mockUserAdmin = new User();
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
     * This test case verifies that the test endpoint is working
     * @throws Exception
     */
    @Test
    public void testTestEndpoint() throws Exception {
        mockMvc.perform(get("/users/test"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello World"));
    }

    /**
     * This test case verifies that the postNewUser endpoint is working
     * @throws Exception
     */
    @Test
    public void testPostNewUserEndpoint() throws Exception {
        when(userRepository.saveAndFlush(mockUserAdmin)).thenReturn(mockUserAdmin);

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validUserAdminJson))
                .andExpect(status().isCreated());
    }

    /**
     * This test case verifies that the getAllUsers endpoint is working
     * @throws Exception
     */
    @Test
    public void testGetAllUsersEndpoint() throws Exception {
        List<User> users = List.of(mockUserAdmin, mockUserCustomer);

        when(userRepository.findAll()).thenReturn(users);

        mockMvc.perform(get("/users/all")
                        .headers(headersAdmin))
                .andExpect(status().isOk());
    }

    /**
     * This test case verifies that the getAllUsers endpoint is forbidden for customers
     * @throws Exception
     */
    @Test
    public void testGetAllUsersEndpointForbidden() throws Exception {
        List<User> users = List.of(mockUserAdmin, mockUserCustomer);

        when(userRepository.findAll()).thenReturn(users);

        mockMvc.perform(get("/users/all")
                        .headers(headersCustomer))
                .andExpect(status().isForbidden());
    }

    /**
     * This test case verifies that the getUserById endpoint is working
     * @throws Exception
     */
    @Test
    public void testGetUserByIdEndpoint() throws Exception {
        when(userRepository.findById(1)).thenReturn(Optional.of(mockUserAdmin));

        mockMvc.perform(get("/users/id/1")
                        .headers(headersAdmin))
                .andExpect(status().isOk());
    }

    /**
     * This test case verifies that the getUserById endpoint is forbidden for customers
     * @throws Exception
     */
    @Test
    public void testGetUserByIdEndpointForbidden() throws Exception {
        when(userRepository.findById(1)).thenReturn(Optional.of(mockUserAdmin));

        mockMvc.perform(get("/users/id/1")
                        .headers(headersCustomer))
                .andExpect(status().isForbidden());
    }

    /**
     * This test case verifies that the getUser endpoint is working
     * @throws Exception
     */
    @Test
    public void testGetUserEndpoint() throws Exception {
        when(userRepository.findById(1)).thenReturn(Optional.of(mockUserAdmin));

        mockMvc.perform(get("/users/user")
                        .headers(headersAdmin))
                .andExpect(status().isOk());
    }

    /**
     * This test case verifies that the updateUser endpoint is working
     * @throws Exception
     */
    @Test
    public void testUpdateUserEndpoint() throws Exception {
        when(userRepository.findById(1)).thenReturn(Optional.of(mockUserAdmin));
        when(userRepository.saveAndFlush(mockUserAdmin)).thenReturn(mockUserAdmin);

        mockMvc.perform(put("/users/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validUserAdminJson)
                        .headers(headersAdmin))
                .andExpect(status().isOk());
    }

    /**
     * This test case verifies that the deleteAllUsers endpoint is working
     * @throws Exception
     */
    @Test
    public void testDeleteAllUsersEndpoint() throws Exception {
        mockMvc.perform(delete("/users/all")
                        .headers(headersAdmin))
                .andExpect(status().isOk());
    }

    /**
     * This test case verifies that the deleteAllUsers endpoint is forbidden for customers
     * @throws Exception
     */
    @Test
    public void testDeleteAllUsersEndpointForbidden() throws Exception {
        mockMvc.perform(delete("/users/all")
                        .headers(headersCustomer))
                .andExpect(status().isForbidden());
    }

    /**
     * This test case verifies that the deleteUserById endpoint is working
     * @throws Exception
     */
    @Test
    public void testDeleteUserByIdEndpoint() throws Exception {
        when(userRepository.findById(1)).thenReturn(Optional.of(mockUserAdmin));

        mockMvc.perform(delete("/users/id/1")
                        .headers(headersAdmin))
                .andExpect(status().isOk());
    }

    /**
     * This test case verifies that the deleteUserById endpoint is forbidden for customers
     * @throws Exception
     */
    @Test
    public void testDeleteUserByIdEndpointForbidden() throws Exception {
        when(userRepository.findById(1)).thenReturn(Optional.of(mockUserAdmin));

        mockMvc.perform(delete("/users/id/1")
                        .headers(headersCustomer))
                .andExpect(status().isForbidden());
    }
}
