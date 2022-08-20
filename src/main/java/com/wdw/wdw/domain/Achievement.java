package com.wdw.wdw.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Achievement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "achievement_seq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "badge_seq")
    private Badge badge;


    public void setBadge(Badge badge) {
        this.badge = badge;
        badge.getAchievements().add(this);
    }

    public void setUser(User user) {
        this.user = user;
        user.getAchievements().add(this);
    }
}
