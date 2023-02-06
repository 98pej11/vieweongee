package com.ssafy.vieweongee.repository;

import com.ssafy.vieweongee.entity.Participant;
import com.ssafy.vieweongee.entity.ParticipantId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ParticipantRepository extends JpaRepository<Participant, ParticipantId> {
    @Query("select p from Participant p where p.participant_id.study.id = :id")
    List<Participant> findAllByStudyId(@Param("id") Long study_id);
}
