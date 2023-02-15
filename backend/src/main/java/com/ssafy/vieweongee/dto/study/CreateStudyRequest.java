package com.ssafy.vieweongee.dto.study;

import com.ssafy.vieweongee.entity.Study;
import com.ssafy.vieweongee.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class CreateStudyRequest {
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
    private LocalDateTime study_datetime;

    @Builder
    public CreateStudyRequest(int running_time, int personnel, String title, String company, String job, String type, String content, boolean attitude, boolean ability, boolean teamwork, boolean solving, boolean loyalty, boolean confirm, LocalDateTime study_datetime) {
        this.running_time = running_time;
        this.personnel = personnel;
        this.title = title;
        this.company = company;
        this.job = job;
        this.type = type;
        this.content = content;
        this.attitude = attitude;
        this.ability = ability;
        this.teamwork = teamwork;
        this.solving = solving;
        this.loyalty = loyalty;
        this.confirm = confirm;
        this.study_datetime = study_datetime;
    }

    public Study toEntity(User user) {
        return Study.builder()
                .user(user)
                .running_time(running_time)
                .personnel(personnel)
                .title(title)
                .company(company)
                .job(job)
                .type(type)
                .content(content)
                .attitude(attitude)
                .ability(ability)
                .teamwork(teamwork)
                .solving(solving)
                .loyalty(loyalty)
                .confirm(confirm)
                .study_datetime(study_datetime)
                .regist_datetime(LocalDateTime.now())
                .build();
    }
}
