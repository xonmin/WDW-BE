package com.wdw.wdw.controller;

import com.wdw.wdw.dto.record.RecordAddRequestDto;
import com.wdw.wdw.dto.record.RecordAddResponseDto;
import com.wdw.wdw.dto.record.RecordGetWaterListResponseDto;
import com.wdw.wdw.dto.record.RecordGetDailyWaterResponseDto;
import com.wdw.wdw.infra.ApiResponse;
import com.wdw.wdw.infra.jwt.PrincipalDetails;
import com.wdw.wdw.service.RecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;

    @PostMapping(value = "/record/add")
    public ApiResponse<RecordAddResponseDto> add(@AuthenticationPrincipal PrincipalDetails details, @RequestBody RecordAddRequestDto req) {
        log.info("기록 추가");
        return ApiResponse.success(HttpStatus.OK, recordService.addRecord(details.getUser(), req));
    }

    @GetMapping(value = "/record/today")
    public ApiResponse<RecordGetDailyWaterResponseDto> getDaily(@AuthenticationPrincipal PrincipalDetails details) {
        log.info("일간 기록 조회");
        return ApiResponse.success(HttpStatus.OK, recordService.findDailyRecord(details.getUser(), LocalDate.now()));
    }

    @GetMapping(value = "/record/week")
    public ApiResponse<RecordGetWaterListResponseDto> getWeekly(@AuthenticationPrincipal PrincipalDetails details) {
        log.info("주간 기록 조회");
        return ApiResponse.success(HttpStatus.OK, recordService.findWeeklyRecord(details.getUser(), LocalDate.now()));
    }

    @GetMapping(value = "/record/month")
    public ApiResponse<RecordGetWaterListResponseDto> getMonthly(@AuthenticationPrincipal PrincipalDetails details) {
        log.info("월간 기록 조회");
        return ApiResponse.success(HttpStatus.OK, recordService.findMonthlyRecord(details.getUser(), LocalDate.now()));
    }
}
