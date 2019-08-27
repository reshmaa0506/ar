package com.example.zoom.Repository;

import com.example.zoom.entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthUserRepository extends JpaRepository<AuthUser,Long> {

    AuthUser findByEmailAndIsDeletedFalseAndIsDisabledFalse(String email);
}
