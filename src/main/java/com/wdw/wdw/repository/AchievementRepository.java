package com.wdw.wdw.repository;

import com.wdw.wdw.domain.Achievement;
import com.wdw.wdw.domain.BadgeType;
import com.wdw.wdw.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AchievementRepository extends JpaRepository<Achievement, Long> {
    List<Achievement> findAchievementsByUserAndBadge_BadgeType(User user, BadgeType badgeType);

}
