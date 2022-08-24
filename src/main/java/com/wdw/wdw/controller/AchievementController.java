package com.wdw.wdw.controller;

import com.wdw.wdw.domain.User;
import com.wdw.wdw.infra.jwt.PrincipalDetails;
import com.wdw.wdw.service.AchievementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AchievementController {
    private final AchievementService achievementService;

    @PostMapping(value = "/achievement/add/{badgeId}")
    public String addAchievement(@PathVariable("badgeId") Long badgeId, Authentication authentication) {
        try {
            PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
            User findUser = principal.getUser();

            achievementService.addAchievement(findUser.getId(), badgeId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "배지 등록 완료";
    }
}
