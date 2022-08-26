package com.wdw.wdw.service;

import com.wdw.wdw.domain.Achievement;
import com.wdw.wdw.domain.Badge;
import com.wdw.wdw.domain.BadgeType;
import com.wdw.wdw.domain.User;
import com.wdw.wdw.repository.AchievementRepository;
import com.wdw.wdw.repository.BadgeRepository;
import com.wdw.wdw.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AchievementService {

    private final AchievementRepository achievementRepository;
    private final UserRepository userRepository;
    private final BadgeRepository badgeRepository;

    public List<Achievement> findAllAchievements() {
        return achievementRepository.findAll();
    }

    public BadgeType checkWaterIntake(User user) {
        Integer curIntake = user.getWaterIntake();

        if (curIntake >= 10000 && curIntake < 50000) {
            return BadgeType.SUM_BRONZE;
        } else if (curIntake >= 50000 && curIntake < 100000) {
            return BadgeType.SUM_SILVER;
        } else if (curIntake >= 100000) {
            return BadgeType.SUM_GOLD;
        }
        return BadgeType.SUM_BASIC;
    }

    public BadgeType checkConsecutiveDays(User user) {
        Integer curConDays = user.getConsecutiveDays();
        if (curConDays >= 7) {
            return BadgeType.CON_BRONZE;
        } else if (curConDays > 30 && curConDays < 180) {
            return BadgeType.CON_SILVER;
        } else if (curConDays >= 180) {
            return BadgeType.CON_GOLD;
        }
        return BadgeType.CON_BASIC;
    }

    public boolean checkIfInList(User user, BadgeType badgeType) {
        List<Achievement> foundByBadge = achievementRepository.findAchievementsByUserAndBadge_BadgeType(user, badgeType);
        boolean checkFlag = false;
        if (!foundByBadge.isEmpty()) {
            checkFlag = true;
        }
        return checkFlag;
    }

    @Transactional
    public Long addAchievement(Long userId) {
        Optional<User> findUser = userRepository.findById(userId);

        BadgeType waterType = checkWaterIntake(findUser.get());
        Optional<Badge> waterBadge = badgeRepository.findByBadgeType(waterType);

        BadgeType dayType = checkConsecutiveDays(findUser.get());
        Optional<Badge> dayBadge = badgeRepository.findByBadgeType(dayType);

        //achievement 등록
        Achievement waterAchievement = Achievement.createAchievement(waterBadge.get());
        Achievement dayAchievement = Achievement.createAchievement(dayBadge.get());
        if (findUser.isPresent()) {
            if (!checkIfInList(findUser.get(), waterType)) {
                findUser.get().addNewAchievement(waterAchievement);
            }
            if (!checkIfInList(findUser.get(), dayType)) {
                findUser.get().addNewAchievement(dayAchievement);
            }
        } else {
            throw new IllegalStateException("찾을 수 없는 유저");
        }

        return waterAchievement.getId();
    }
}
