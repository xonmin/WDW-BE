package com.wdw.wdw.controller;

import com.wdw.wdw.domain.Record;
import com.wdw.wdw.domain.User;
import com.wdw.wdw.infra.jwt.PrincipalDetails;
import com.wdw.wdw.service.AchievementService;
import com.wdw.wdw.service.RecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;
    private final AchievementService achievementService;

    @GetMapping(value = "/record/today")
    public String getDayRecord(@AuthenticationPrincipal PrincipalDetails details) {
        LocalDateTime currentDate = LocalDateTime.now();
        Long userId = details.getUser().getId();
        List<Record> findTodayRecord = recordService.findRecordByDay(currentDate, userId);

        return "일별 조회 완료";
    }

    @GetMapping(value = "/record/week")
    public String getWeekRecord() {
        LocalDateTime currentDate = LocalDateTime.now();
        List<Record> findWeekRecord = recordService.findRecordByWeek(currentDate);

        return "일별 조회 완료";
    }

    @GetMapping(value = "/record/month")
    public String getMonthRecord() {
        LocalDateTime currentDate = LocalDateTime.now();
        List<Record> recordByMonth = recordService.findRecordByMonth(currentDate);

        return "일별 조회 완료";
    }

    @PostMapping(value = "/record/add")
    public String addRecord(@RequestBody Record record, Authentication authentication) {
        try {
            PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
            User findUser = principal.getUser();
            System.out.println("findUser = " + findUser.getUsername());
            record.setUser(findUser);

            recordService.addRecord(record);
            achievementService.addAchievement(record.getUser().getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "기록 저장 완료";

    }
}
