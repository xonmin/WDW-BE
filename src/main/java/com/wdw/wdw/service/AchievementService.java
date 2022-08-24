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

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AchievementService {

    private final AchievementRepository achievementRepository;
    private final UserRepository userRepository;
    private final BadgeRepository badgeRepository;

    public List<Achievement> findAllAchievements() {
        return achievementRepository.findAll();
    }

    public void addAchievement(Long userId, Long badgeId) {
        Achievement newAchievement = new Achievement();

        Optional<User> findUser = userRepository.findById(userId);
        Optional<Badge> findBadge = badgeRepository.findById(badgeId);

        if (findUser.isPresent()) {
            if (findBadge.isPresent()) {
                newAchievement.setUser(findUser.get());
                newAchievement.setBadge(findBadge.get());
            } else {
                throw new IllegalStateException("등록되지 않은 배지입니다");
            }
        }else{
            throw new IllegalStateException("User를 찾을 수 없습니다");
        }
        achievementRepository.save(newAchievement);
    }

}
