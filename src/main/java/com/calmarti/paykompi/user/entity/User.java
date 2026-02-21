package com.calmarti.paykompi.user.entity;

import com.calmarti.paykompi.user.enums.UserRole;
import com.calmarti.paykompi.user.enums.UserStatus;
import com.calmarti.paykompi.user.enums.UserType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name="USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable=false, unique = true)
    private String username;
    @Column(nullable = false)
    private String passwordHash;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType userType;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;
    @Column(nullable = false)
    @CreationTimestamp
    private Instant createdAt;
}
