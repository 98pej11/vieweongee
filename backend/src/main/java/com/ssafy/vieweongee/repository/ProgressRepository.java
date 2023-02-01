package com.ssafy.vieweongee.repository;

import com.ssafy.vieweongee.entity.Participant;
import com.ssafy.vieweongee.entity.ParticipantId;
import com.ssafy.vieweongee.entity.Progress;
import com.ssafy.vieweongee.entity.ProgressId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgressRepository extends JpaRepository<Progress, ProgressId> {

}
