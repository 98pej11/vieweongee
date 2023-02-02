package com.ssafy.vieweongee.repository;

import com.ssafy.vieweongee.entity.Progress;
import com.ssafy.vieweongee.entity.ProgressId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

public interface ProgressRepository extends JpaRepository<Progress, ProgressId> {
    @Query("select p from Progress p where p.progress_id.user.id = :id")
    List<Progress> findByUser_id(@Param("id") Long user_id);
    @Query("select p from Progress p where p.progress_id.user.id = :id and p.status = :status")
    List<Progress> findByUser_idAndStatus(@Param("id") Long user_id, @Param("status") boolean status);

}
