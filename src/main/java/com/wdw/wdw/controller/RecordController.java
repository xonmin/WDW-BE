package com.wdw.wdw.controller;

import com.wdw.wdw.infra.jwt.PrincipalDetails;
import com.wdw.wdw.infra.jwt.JwtTokenProvider;

import com.wdw.wdw.domain.Record;
import com.wdw.wdw.domain.User;

import com.wdw.wdw.repository.UserRepository;
import com.wdw.wdw.service.RecordService;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping(value = "/record/today")
    public String getDayRecord(){
        LocalDateTime currentDate = LocalDateTime.now();
        List<Record> findTodayRecord = recordService.findRecordByDay(currentDate);

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
        } catch (Exception e) {
            e.printStackTrace();
        }

        recordService.addRecord(record);

        return "기록 저장 완료";
    }
}
