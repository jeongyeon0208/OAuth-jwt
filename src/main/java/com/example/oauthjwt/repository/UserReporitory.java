package com.example.oauthjwt.repository;

import com.example.oauthjwt.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserReporitory extends JpaRepository<UserEntity, Long> {

    UserEntity findByUsername(String username);
}
