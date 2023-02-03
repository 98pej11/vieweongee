package com.ssafy.vieweongee.repository;

import com.ssafy.vieweongee.entity.Study;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRepository extends JpaRepository<Study, Long> {
    Study findByIdAndConfirm(Long id, boolean confirm);
}
