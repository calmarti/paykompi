package com.calmarti.paykompi.user.repository;

import com.calmarti.paykompi.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
