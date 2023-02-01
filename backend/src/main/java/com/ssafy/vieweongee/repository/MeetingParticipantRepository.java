package com.ssafy.vieweongee.repository;

import com.ssafy.vieweongee.entity.Participant;
import com.ssafy.vieweongee.entity.ParticipantId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MeetingParticipantRepository extends JpaRepository<Participant, ParticipantId> {
    @Query(value = "select p from Participant p where p.participant_id.study.id = ?1")
    List<Participant> findAllByStudy_id(Long studyId);
}
