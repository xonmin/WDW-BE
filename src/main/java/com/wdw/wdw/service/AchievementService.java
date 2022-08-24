package com.wdw.wdw.service;

import com.wdw.wdw.domain.Achievement;
import com.wdw.wdw.domain.Badge;
import com.wdw.wdw.domain.BadgeType;
import com.wdw.wdw.domain.User;
import com.wdw.wdw.repository.AchievementRepository;
import com.wdw.wdw.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AchievementService {

    private final AchievementRepository achievementRepository;
    private final UserRepository userRepository;

    public List<Achievement> findAllAchievements() {
        return achievementRepository.findAll();
    }

    public void addAchievement(Long userId, Long badgeId) {
        Achievement newAchievement = new Achievement();

        Optional<User> findUser = userRepository.findById(userId);

        if (findUser.isPresent()) {
            Badge addBadge = new Badge();
            addBadge.setBadgeType(checkWaterIntake(findUser.get()));
            newAchievement.setUser(findUser.get());
            newAchievement.setBadge(addBadge);
        }else{
            throw new IllegalStateException("User를 찾을 수 없습니다");
        }
        achievementRepository.save(newAchievement);
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
}
