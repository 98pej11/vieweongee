package com.ssafy.vieweongee.repository;

import com.ssafy.vieweongee.entity.Summary;
import com.ssafy.vieweongee.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SummaryRepository extends JpaRepository<Summary, User> {
    @Query("select s from Summary s where s.user.id=:id")
    Summary findById(@Param("id") Long id);
}
