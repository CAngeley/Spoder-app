package com.revature.spoder_app.util.auth;

import com.revature.spoder_app.Security.JwtGenerator;
import com.revature.spoder_app.User.User;
import com.revature.spoder_app.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtGenerator jwtGenerator;
    private final UserService userService;

    @Autowired
    public AuthController(AuthenticationManager authManager, JwtGenerator jwtGenerator, UserService userService) {
        this.authManager = authManager;
        this.jwtGenerator = jwtGenerator;
        this.userService = userService;
    }

    @PostMapping("/users/login")
    public ResponseEntity<?> postLogin(@RequestParam String email, @RequestParam String password) {
        try {
            Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            User user = userService.findByEmail(email);
            int id = user.getUserId();
            String token = jwtGenerator.generateToken(authentication, id);
            HttpHeaders headers = new HttpHeaders();
            headers.set("userType", user.getUserType().name());
            headers.set("userId", String.valueOf(user.getUserId()));

            return ResponseEntity.ok().headers(headers).body(new AuthResponseDTO(token));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }
}
