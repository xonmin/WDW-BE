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

        if (curIntake >= 5000 && curIntake < 10000) {
            boolean checkFlag = false;
            for (Achievement achievement : user.getAchievements()) {
                if (achievement.getBadge().equals(BadgeType.SUM_BRONZE)) {
                    checkFlag = true;
                }
            }
            if (!checkFlag) {
                return BadgeType.SUM_BRONZE;
            }
        } else if (curIntake >= 10000 && curIntake < 50000) {
            boolean checkFlag = false;
            for (Achievement achievement : user.getAchievements()) {
                if (achievement.getBadge().equals(BadgeType.SUM_SILVER)) {
                    checkFlag = true;
                }
            }
            if (!checkFlag) {
                return BadgeType.SUM_SILVER;
            }
        } else if (curIntake >= 50000) {
            boolean checkFlag = false;
            for (Achievement achievement : user.getAchievements()) {
                if (achievement.getBadge().equals(BadgeType.SUM_GOLD)) {
                    checkFlag = true;
                }
            }
            if (!checkFlag) {
                return BadgeType.SUM_GOLD;
            }
        }
        return BadgeType.SUM_BASIC;
    }

    public boolean checkIfInList(User user, BadgeType badgeType) {
        List<Achievement> achievements = user.getAchievements();
        boolean checkFlag = false;
        for (Achievement achievement : achievements) {
            if (achievement.getBadge().equals(badgeType)) {
                checkFlag = true;
            }
        }
        return checkFlag;
    }

    @Transactional
    public Long addAchievement(Long userId) {
        Optional<User> findUser = userRepository.findById(userId);

        //badge찾아서
        BadgeType type = checkWaterIntake(findUser.get());
        Optional<Badge> findBadge = badgeRepository.findByBadgeType(type);

        //achievement 등록
        Achievement achievement = Achievement.createAchievement(findBadge.get());
        if (findUser.isPresent()) {
            findUser.get().addNewAchievement(achievement);
        } else {
            throw new IllegalStateException("찾을 수 없는 유저");
        }

        return achievement.getId();
    }
}
