package com.revature.spoder_app.User;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    private String firstName;
    private String lastName;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(name = "user_type", columnDefinition = "varchar(10) default 'CUSTOMER'")
    @Enumerated(EnumType.STRING)
    private userType userType;

    public enum userType {
        CUSTOMER, EMPLOYEE, ADMIN
    }

}
