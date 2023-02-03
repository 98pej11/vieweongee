package com.ssafy.vieweongee.repository;

import com.ssafy.vieweongee.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
//    User findById(Long id);
}
