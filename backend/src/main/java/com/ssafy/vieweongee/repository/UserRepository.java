package com.ssafy.vieweongee.repository;

import com.ssafy.vieweongee.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.email=:email")
    User findByEmailUser(@Param("email") String email);

    boolean existsByEmail(String email);
    boolean existsByName(String name);

    @Query("SELECT u FROM User u WHERE u.jwt_token=:token")
    Optional<User> findByJwtToken(String token);

    List<User> getUserByEmail(String email);
    @Query("SELECT u FROM User u WHERE u.email=:email and u.provider=:social")
    User getUserByEmailandSocial(@Param("email") String email, @Param("social") String social);

    User findByEmailAndProvider(String email, String provider);
    @Query("SELECT u FROM User u WHERE u.email=:email")
    User getUserByEmailUser(@Param("email") String email);

    User getUserById(Long id);

    @Modifying
    @Query("UPDATE User u SET u.jwt_token=null WHERE u.id=:id")
    void logoutUpdate(@Param("id") Long id);

    @Query("select u.email from User u where u.id=:id")
    String getEmailById(@Param("id") Long id);

//    @Query("DELETE u FROM User u WHERE u.email=:email and u.social_login=:social")
//    void deleteByJwtToken(String refreshToken);
}

