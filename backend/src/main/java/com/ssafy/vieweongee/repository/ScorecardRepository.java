package com.ssafy.vieweongee.repository;

import com.ssafy.vieweongee.entity.Scorecard;
import com.ssafy.vieweongee.entity.ScorecardId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ScorecardRepository extends JpaRepository<Scorecard, ScorecardId> {
    @Query("select p from Scorecard p where p.score_id.user.id = :userId and p.score_id.study.id = :studyId")
    Scorecard findFeedback(@Param("userId") Long user_id, @Param("studyId") Long study_id);
}
