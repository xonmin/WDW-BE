package com.wdw.wdw.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Badge {

    @Id
    @GeneratedValue
    @Column(name = "badge_seq")
    private Long id;

    @Enumerated(EnumType.STRING)
    private BadgeType badgeType;

    @OneToMany(mappedBy = "badge")
    private List<Achievement> achievements = new ArrayList<>();

    private String badgeName;
    private String badgeImage;

    public void setBadgeType(BadgeType badgeType) {
        this.badgeType = badgeType;
    }
}
