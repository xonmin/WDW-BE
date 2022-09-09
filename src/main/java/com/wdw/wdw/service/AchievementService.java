package com.wdw.wdw.service;

import com.wdw.wdw.domain.Achievement;
import com.wdw.wdw.domain.Badge;
import com.wdw.wdw.domain.BadgeType;
import com.wdw.wdw.domain.User;
import com.wdw.wdw.dto.user.UserBadgeResponseDto;
import com.wdw.wdw.exception.EntityNotFoundException;
import com.wdw.wdw.repository.AchievementRepository;
import com.wdw.wdw.repository.BadgeRepository;
import com.wdw.wdw.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

        if (curIntake >= BadgeType.SUM_GOLD.getIntakeOfSum()) {
            return BadgeType.SUM_GOLD;
        } else if (curIntake >= BadgeType.SUM_SILVER.getIntakeOfSum()) {
            return BadgeType.SUM_SILVER;
        } else if (curIntake >= BadgeType.SUM_BRONZE.getIntakeOfSum()) {
            return BadgeType.SUM_BRONZE;
        } else {
            return BadgeType.SUM_BASIC;
        }
    }

    public BadgeType checkConsecutiveDays(User user) {
        Integer curConDays = user.getConsecutiveDays();

        if (curConDays >= BadgeType.CON_GOLD.getConsecutiveDay()) {
            return BadgeType.CON_GOLD;
        } else if (curConDays >= BadgeType.CON_SILVER.getConsecutiveDay()) {
            return BadgeType.CON_SILVER;
        } else if (curConDays >= BadgeType.CON_BRONZE.getConsecutiveDay()) {
            return BadgeType.CON_BRONZE;
        } else {
            return BadgeType.CON_BASIC;
        }
    }

    public boolean checkIfInList(User user, BadgeType badgeType) {
        List<Achievement> foundByBadge = achievementRepository.findAchievementsByUserAndBadge_BadgeType(user, badgeType);
        boolean checkFlag = !foundByBadge.isEmpty();
        return !checkFlag;
    }

    @Transactional
    public Long addAchievement(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(EntityNotFoundException::new);
        BadgeType waterType = checkWaterIntake(user);
        Badge waterBadge = badgeRepository.findByBadgeType(waterType)
                .orElseThrow(EntityNotFoundException::new);
        BadgeType dayType = checkConsecutiveDays(user);
        Badge dayBadge = badgeRepository.findByBadgeType(dayType)
                .orElseThrow(EntityNotFoundException::new);

        // achievement 등록
        Achievement waterAchievement = Achievement.createAchievement(waterBadge);
        Achievement dayAchievement = Achievement.createAchievement(dayBadge);

        if (checkIfInList(user, waterType)) {
            user.addNewAchievement(waterAchievement);
        }
        if (checkIfInList(user, dayType)) {
            user.addNewAchievement(dayAchievement);
        }

        return waterAchievement.getId();
    }

    public UserBadgeResponseDto getBadgeList(User user) {
        List<Badge> badgeList = achievementRepository.findAchievementsByUser(user)
                .stream()
                .map(Achievement::getBadge)
                .collect(Collectors.toList());
        return UserBadgeResponseDto.from(badgeList);
    }
}
