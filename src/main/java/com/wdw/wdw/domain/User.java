package com.wdw.wdw.domain;

import com.wdw.wdw.dto.user.UserUpdateRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_seq")
    private Long id;

    private String username;

    private String password;

    private String name;

    private Integer weight;

    private String roles; // USER, ADMIN

    private String provider;

    private String providerId;

    private Integer consecutiveDays;

    private Integer waterIntake;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Achievement> achievements = new ArrayList<>();

    @CreationTimestamp
    private Timestamp createDate;

    public void setConsecutiveDays(Integer consecutiveDays) {
        this.consecutiveDays = consecutiveDays;
    }

    public void appendWaterIntake(Integer waterIntake) {
        this.waterIntake += waterIntake;
    }

    public void appendConsecutiveDays(Integer days) {
        this.consecutiveDays += days;
    }

    public List<String> getRoleList() {
        if (this.roles.length() > 0) {
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

    @Builder
    public User(Long id, String username, String password, String name, Integer weight, String roles, String provider, String providerId, Integer consecutiveDays, Integer waterIntake) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.weight = weight;
        this.roles = roles;
        this.provider = provider;
        this.providerId = providerId;
        this.consecutiveDays = consecutiveDays;
        this.waterIntake = waterIntake;
    }

    public void userUpdate(UserUpdateRequestDto req) {
        this.password = req.getPassword();
        this.name = req.getName();
        this.weight = req.getWeight();
    }

    public Integer getGoalAmount() {
        return this.weight * 30;
    }

    //Achievement 생성 메소드
    public void addNewAchievement(Achievement achievement) {
        achievements.add(achievement);
        achievement.setUser(this);
    }
}
