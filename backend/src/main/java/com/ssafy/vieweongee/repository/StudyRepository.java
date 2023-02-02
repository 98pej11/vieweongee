package com.ssafy.vieweongee.repository;

import com.ssafy.vieweongee.entity.Study;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudyRepository extends JpaRepository<Study, Long> {
}
