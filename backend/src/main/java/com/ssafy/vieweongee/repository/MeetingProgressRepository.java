package com.ssafy.vieweongee.repository;

import com.ssafy.vieweongee.entity.Progress;
import com.ssafy.vieweongee.entity.ProgressId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MeetingProgressRepository extends JpaRepository<Progress, ProgressId> {
    @Query(value = "select p from Progress p where p.progress_id.study.id = ?1")
    List<Progress> findAllByStudy_id(Long studyId);
}
