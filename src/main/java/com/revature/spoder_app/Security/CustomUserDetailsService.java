package com.revature.spoder_app.Security;

import com.revature.spoder_app.User.User;
import com.revature.spoder_app.User.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * This class is used to load user-specific data when trying to authenticate a user.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final ArrayList<String> roles = new ArrayList<>();

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
        roles.add("CUSTOMER");
        roles.add("ADMIN");
    }

    /**
     * This method is used to load a user by their email.
     * @param email The email of the user to load.
     * @return The user details of the user with the given email.
     * @throws UsernameNotFoundException If the email is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Email not found"));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(roles));
    }

    /**
     * This method is used to map the roles of a user to authorities.
     * @param roles The roles of the user.
     * @return The authorities of the user.
     */
    private Collection<GrantedAuthority> mapRolesToAuthorities(List<String> roles) {
        return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}
