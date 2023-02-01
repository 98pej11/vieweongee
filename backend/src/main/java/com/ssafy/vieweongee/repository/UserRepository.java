package com.ssafy.vieweongee.repository;

import com.ssafy.vieweongee.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);
    User findByEmailAndProvider(String email, String provider);
}
