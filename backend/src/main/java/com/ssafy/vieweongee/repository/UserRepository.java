package com.ssafy.vieweongee.repository;

import com.ssafy.vieweongee.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
