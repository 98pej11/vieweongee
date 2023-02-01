package com.ssafy.vieweongee.repository;

import com.ssafy.vieweongee.entity.Scorecard;
import com.ssafy.vieweongee.entity.ScorecardId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScoreRepository extends JpaRepository <Scorecard, ScorecardId> {
    /**
     * 스터디 아이디로 스터디원들의 채점표 가져오기
     * @param study_id
     * @return
     */
    @Query(value = "select s from Scorecard s where s.score_id.study.id = ?1")
    List<Scorecard> findAllByStudyId(Long study_id);
}
