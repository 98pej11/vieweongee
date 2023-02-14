package com.ssafy.vieweongee.entity;

import com.ssafy.vieweongee.dto.study.CreateStudyRequest;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Study {
    // 스터디 번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 호스트 id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    // 스터디 진행 시간
    private int running_time;
    // 스터디 희망 인원
    private int personnel;

    // 스터디 모집글 제목
    @Column(length = 25)
    @NotNull
    private String title;
    // 기업
    @Column(length = 15)
    @NotNull
    private String company;
    // 직무
    @Column(length = 50)
    @NotNull
    private String job;
    // 스터디 유형 (일대일, 일대다, 다대다)
    @Column(length = 5)
    @NotNull
    private String type;
    // 스터디 모집글 내용
    @Column(length = 500)
    private String content;

    // 채점표 항목 - 태도 사용 여부
    @ColumnDefault("true")
    @Column(columnDefinition = "TINYINT(1)")
    private boolean attitude;
    // 채점표 항목 - 직무 역량 사용 여부
    @ColumnDefault("true")
    @Column(columnDefinition = "TINYINT(1)")
    private boolean ability;
    // 채점표 항목 - 팀워크 사용 여부
    @ColumnDefault("true")
    @Column(columnDefinition = "TINYINT(1)")
    private boolean teamwork;
    // 채점표 항목 - 문제 해결 사용 여부
    @ColumnDefault("true")
    @Column(columnDefinition = "TINYINT(1)")
    private boolean solving;
    // 채점표 항목 - 기업 이해도 사용 여부
    @ColumnDefault("true")
    @Column(columnDefinition = "TINYINT(1)")
    private boolean loyalty;
    // 스터디 확정 여부
    @ColumnDefault("false")
    @Column(columnDefinition = "TINYINT(1)")
    private boolean confirm;

    // 스터디 진행 날짜 및 시간
    @NotNull
    private LocalDateTime study_datetime;

    // 스터디 모집글 등록 날짜 및 시간
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date regist_datetime;

    @OneToMany(mappedBy = "study", cascade = CascadeType.ALL)
    private List<Alarm> alarms = new ArrayList<>();

    @OneToMany(mappedBy = "participant_id.study", cascade = CascadeType.ALL)
    private List<Participant> participants = new ArrayList<>();

    @OneToMany(mappedBy = "progress_id.study", cascade = CascadeType.ALL)
    private List<Progress> progresses = new ArrayList<>();

    @OneToMany(mappedBy = "score_id.study", cascade = CascadeType.ALL)
    private List<Scorecard> scorecards = new ArrayList<>();

    @OneToMany(mappedBy = "study", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Study(Long id, User user, int running_time, int personnel, String title, String company, String job, String type, String content, boolean attitude, boolean ability, boolean teamwork, boolean solving, boolean loyalty, boolean confirm, LocalDateTime study_datetime, Date regist_datetime, List<Alarm> alarms, List<Participant> participants, List<Progress> progresses, List<Scorecard> scorecards, List<Comment> comments) {
        this.id = id;
        this.user = user;
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
        this.regist_datetime = regist_datetime;
        this.alarms = alarms;
        this.participants = participants;
        this.progresses = progresses;
        this.scorecards = scorecards;
        this.comments = comments;
    }

    // 0212 추가
    @Builder
    public Study(CreateStudyRequest createStudyRequest){
        this.title=createStudyRequest.getTitle();
        this.content= createStudyRequest.getContent();
        this.company=createStudyRequest.getCompany();
    }

    // 테스트 코드 용
    public void setId(long l) {
        this.id=l;
    }

    public void updateConfrim(boolean b) {
        this.confirm=b;
    }
}
