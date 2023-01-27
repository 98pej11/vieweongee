package com.ssafy.vieweongee.entity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30)
    private String email;
    @Column(length = 16)
    private String password;
    @Column(length = 10)
    private String social_login;

    @Column
    private String social_token;

    @Column(length = 10)

    @NotNull
    private String nickname;
    @ColumnDefault("false")
    @Column(columnDefinition = "TINYINT(1)")
    private boolean authority;
    private String jwt_token;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Notice> notices = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Alarm> alarms = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Study> studies = new ArrayList<>();

    @OneToMany(mappedBy = "participant_id.user", cascade = CascadeType.ALL)
    private List<Participant> participants = new ArrayList<>();

    @OneToMany(mappedBy = "progress_id.user", cascade = CascadeType.ALL)
    private List<Progress> progresses = new ArrayList<>();

    @OneToMany(mappedBy = "score_id.user", cascade = CascadeType.ALL)
    private List<Scorecard> scorecards = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Reply> replies = new ArrayList<>();

    @Builder
    public User(Long id, String email, String password, String social_login, String social_token, String nickname, boolean authority, String jwt_token, List<Notice> notices, List<Alarm> alarms, List<Study> studies, List<Participant> participants, List<Progress> progresses, List<Scorecard> scorecards, List<Comment> comments, List<Reply> replies) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.social_login = social_login;
        this.social_token = social_token;
        this.nickname = nickname;
        this.authority = authority;
        this.jwt_token = jwt_token;
        this.notices = notices;
        this.alarms = alarms;
        this.studies = studies;
        this.participants = participants;
        this.progresses = progresses;
        this.scorecards = scorecards;
        this.comments = comments;
        this.replies = replies;
    }
}
