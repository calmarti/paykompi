package com.calmarti.paykompi.user.entity;

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
    @GeneratedValue
    private UUID id;
    @Column(unique = true)
    private String email;
    private String passwordHash;
    private String firstName;
    private String lastName;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserType usertype;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus userStatus;
    @CreationTimestamp
    private Instant createdAt;
}
