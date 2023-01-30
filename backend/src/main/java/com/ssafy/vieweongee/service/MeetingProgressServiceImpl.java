package com.ssafy.vieweongee.service;

import com.ssafy.vieweongee.entity.Progress;
import com.ssafy.vieweongee.repository.MeetingProgressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MeetingProgressServiceImpl implements MeetingProgressService{

    private final MeetingProgressRepository meetingProgressRepository;

    @Autowired
    public MeetingProgressServiceImpl(MeetingProgressRepository meetingProgressRepository) {
        this.meetingProgressRepository = meetingProgressRepository;
    }

    /**
     * 스터디 참가 상태를 변경 (예정 -> 완료)
     * @param studyId
     */
    @Transactional
    @Override
    public void updateStudyProgress(String studyId) {
        System.out.println(">>>>>> service 진입");
        //스터디 아이디로 스터디 참가 이력 가져옴
        List<Progress> list = meetingProgressRepository.findAllByStudy_id(Long.parseLong(studyId));
        System.out.println("스터디 참가 이력 size >> " + list.size());


        //스터디 참가 상태 변경 (예정 >> 완료)
        for(Progress progress : list){
            progress.changeStatusToTrue();
        }
        System.out.println(">>> 스터디 참가 상태 변경 완료");
    }
}
