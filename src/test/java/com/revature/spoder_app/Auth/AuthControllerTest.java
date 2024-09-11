package com.revature.spoder_app.Auth;

import com.revature.spoder_app.Security.JwtGenerator;
import com.revature.spoder_app.User.User;
import com.revature.spoder_app.User.UserService;
import com.revature.spoder_app.util.auth.AuthController;
import com.revature.spoder_app.util.auth.AuthResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


public class AuthControllerTest {

    @Mock
    private AuthenticationManager authManager;

    @Mock
    private JwtGenerator jwtGenerator;

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    /**
     * Test the postLogin method of the AuthController class
     */
    @Test
    public void testPostLogin_Success() {
        String email = "test@example.com";
        String password = "password";
        String token = "jwtToken";
        User mockUser = new User();
        mockUser.setUserId(1);
        mockUser.setEmail(email);

        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);

        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(userService.findByEmail(email)).thenReturn(mockUser);
        when(jwtGenerator.generateToken(authentication, mockUser.getUserId())).thenReturn(token);

        ResponseEntity<?> response = authController.postLogin(email, password);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(new AuthResponseDTO(token), response.getBody());
    }

    /**
     * Test the postLogin method of the AuthController class with invalid credentials
     */
    @Test
    public void testPostLogin_InvalidCredentials() {
        String email = "test@example.com";
        String password = "wrongPassword";

        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(new BadCredentialsException("Invalid credentials"));

        ResponseEntity<?> response = authController.postLogin(email, password);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid credentials", response.getBody());
    }

    /**
     * Test the postLogin method of the AuthController class with an unexpected error
     */
    @Test
    public void testPostLogin_InternalServerError() {
        String email = "test@example.com";
        String password = "password";

        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(new RuntimeException("Unexpected error"));

        ResponseEntity<?> response = authController.postLogin(email, password);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An error occurred", response.getBody());
    }
}