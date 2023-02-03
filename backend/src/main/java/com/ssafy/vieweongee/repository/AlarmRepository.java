package com.ssafy.vieweongee.repository;

import com.ssafy.vieweongee.entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    @Query("select p from Alarm p where p.user.id = :id")
    List<Alarm> findByUser_id(@Param("id") Long user_id);

    @Query("select p from Alarm p where p.user.id = :id and p.see = :see")
    List<Alarm> findByUser_idAndSee(@Param("id") Long user_id, @Param("see") boolean see);
}
