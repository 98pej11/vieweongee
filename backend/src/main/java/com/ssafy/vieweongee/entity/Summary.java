package com.ssafy.vieweongee.entity;

import com.ssafy.vieweongee.dto.user.request.UserCreateRequest;
import lombok.*;
import org.apache.catalina.realm.UserDatabaseRealm;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@ToString
public class Summary implements Serializable {
    @Id
    @Column(name="user_id")
    private Long id;

    //회원 외래키
    @OneToOne
    @MapsId
    @JoinColumn(name="user_id")
    private User user;

    @ColumnDefault("0")
    private int attitude_count;
    @ColumnDefault("0")
    private int attitude_total;
    @ColumnDefault("0")
    private float attitude_average;
    @ColumnDefault("0")
    private int ability_count;
    @ColumnDefault("0")
    private int ability_total;
    @ColumnDefault("0")
    private float ability_average;
    @ColumnDefault("0")
    private int teamwork_count;
    @ColumnDefault("0")
    private int teamwork_total;
    @ColumnDefault("0")
    private float teamwork_average;
    @ColumnDefault("0")
    private int solving_count;
    @ColumnDefault("0")
    private int solving_total;
    @ColumnDefault("0")
    private float solving_average;
    @ColumnDefault("0")
    private int loyalty_count;
    @ColumnDefault("0")
    private int loyalty_total;
    @ColumnDefault("0")
    private float loyalty_average;

    @Builder
    public Summary(Long id, User user, int attitude_count, int attitude_total, float attitude_average, int ability_count, int ability_total, float ability_average, int teamwork_count, int teamwork_total, float teamwork_average, int solving_count, int solving_total, float solving_average, int loyalty_count, int loyalty_total, float loyalty_average) {
        this.id = id;
        this.user = user;
        this.attitude_count = attitude_count;
        this.attitude_total = attitude_total;
        this.attitude_average = attitude_average;
        this.ability_count = ability_count;
        this.ability_total = ability_total;
        this.ability_average = ability_average;
        this.teamwork_count = teamwork_count;
        this.teamwork_total = teamwork_total;
        this.teamwork_average = teamwork_average;
        this.solving_count = solving_count;
        this.solving_total = solving_total;
        this.solving_average = solving_average;
        this.loyalty_count = loyalty_count;
        this.loyalty_total = loyalty_total;
        this.loyalty_average = loyalty_average;
    }

    @Builder
    public Summary(User user){
        this.user=user;
    }


    public void updateCount(int ability, int attitude, int teamwork, int solving, int loyalty){
        this.ability_count += ability;
        this.attitude_count += attitude;
        this.teamwork_count += teamwork;
        this.solving_count += solving;
        this.loyalty_count += loyalty;
    }

    public void updateTotal(int ability, int attitude, int teamwork, int solving, int loyalty){
        this.ability_total += ability;
        this.attitude_total += attitude;
        this.teamwork_total += teamwork;
        this.solving_total += solving;
        this.loyalty_total += loyalty;
    }

    public void updateAverage(float ability, float attitude, float teamwork, float solving, float loyalty){
        this.ability_average = ability;
        this.attitude_average = attitude;
        this.teamwork_average = teamwork;
        this.solving_average = solving;
        this.loyalty_average = loyalty;
    }
}

