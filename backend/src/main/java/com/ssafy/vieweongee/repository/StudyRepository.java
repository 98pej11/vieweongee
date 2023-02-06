package com.ssafy.vieweongee.repository;

import com.ssafy.vieweongee.entity.Study;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudyRepository extends JpaRepository<Study, Long> {
    List<Study> findTop3ByOrderByIdDesc();
    @Query(value = "select * from study s where title regexp :search union select * from study s where content regexp :search union select * from study s where company regexp :search union select * from study s where job regexp :search union select * from study s where type regexp :search order by id desc", nativeQuery = true)
    List<Study> searchByWords(String search);
    Study findByIdAndConfirm(Long id, boolean confirm);

}

