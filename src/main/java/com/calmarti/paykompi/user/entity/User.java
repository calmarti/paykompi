package com.calmarti.paykompi.user.entity;

import com.calmarti.paykompi.common.UserStatus;
import com.calmarti.paykompi.common.UserType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private UUID id;
    @Column(unique = true)
    private String email;
    private String passwordHash;
    private String firstName;
    private String lastName;
    private UserType usertype;
    private UserStatus userStatus;
    private Instant createdAt;
    private Instant updatedAt;

}
