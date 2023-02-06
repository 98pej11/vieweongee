package com.ssafy.vieweongee.dto.study;

import com.ssafy.vieweongee.entity.Study;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class StudyResponse {
    private Long id;
    private Long user_id;
    private String user_nickname;
    private int running_time;
    private int personnel;
    private String title;
    private String company;
    private String job;
    private String type;
    private String content;
    private boolean attitude;
    private boolean ability;
    private boolean teamwork;
    private boolean solving;
    private boolean loyalty;
    private boolean confirm;
    private Date study_datetime;
    private Date regist_datetime;

    public StudyResponse(Study study) {
        this.id = study.getId();
        this.user_id = study.getUser().getId();
        this.user_nickname = study.getUser().getName();
        this.running_time = study.getRunning_time();
        this.personnel = study.getPersonnel();
        this.title = study.getTitle();
        this.company = study.getCompany();
        this.job = study.getJob();
        this.type = study.getType();
        this.content = study.getContent();
        this.attitude = study.isAttitude();
        this.ability = study.isAbility();
        this.teamwork = study.isTeamwork();
        this.solving = study.isSolving();
        this.loyalty = study.isLoyalty();
        this.confirm = study.isConfirm();
        this.study_datetime = study.getStudy_datetime();
        this.regist_datetime = study.getRegist_datetime();
    }
}
