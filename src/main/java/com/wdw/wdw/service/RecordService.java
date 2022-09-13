package com.wdw.wdw.service;

import com.wdw.wdw.domain.Record;
import com.wdw.wdw.domain.User;
import com.wdw.wdw.dto.record.*;
import com.wdw.wdw.infra.util.CalendarUtil;
import com.wdw.wdw.repository.RecordRepository;
import com.wdw.wdw.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;
    private final UserRepository userRepository;
    private final AchievementService achievementService;

    @Transactional
    public RecordAddResponseDto addRecord(User user, RecordAddRequestDto req) {
        user.appendWaterIntake(req.getQuantity());
        if (isEnough(user, LocalDate.now())){
            user.appendConsecutiveDays(1);
        }
        Record record = Record.builder()
                .user(user)
                .quantity(req.getQuantity())
                .date(LocalDate.now())
                .build();
        userRepository.save(user);
        recordRepository.save(record);
        achievementService.addAchievement(user.getId());
        return RecordAddResponseDto.from(user, record);
    }

    public RecordGetDailyWaterResponseDto findDailyRecord(User user, LocalDate date) {
        Integer dailyIntake = recordRepository.findQuantity(user, date);
        if (dailyIntake == null) dailyIntake = 0;
        return RecordGetDailyWaterResponseDto.from(date, dailyIntake);
    }


    public RecordGetWaterListResponseDto findWeeklyRecord(User user, LocalDate date) {
        LocalDate startDay = CalendarUtil.getStartDayOfWeek(date);
        LocalDate endDay = CalendarUtil.getEndDayOfWeek(date);

        List<IntakeRecord> intakeRecord = recordRepository.findPastRecords(user, startDay, endDay);

        List<CustomizedCalendarDto> weeklyRecords = convertIntoDtoList(intakeRecord);

        return RecordGetWaterListResponseDto.from(weeklyRecords);
    }

    public RecordGetWaterListResponseDto findMonthlyRecord(User user, LocalDate date) {
        int curMonth = date.getMonthValue();
        LocalDate startDay = LocalDate.of(date.getYear(), curMonth, 1);

        List<IntakeRecord> intakeRecord = recordRepository.findPastRecords(user, startDay, LocalDate.now());

        List<CustomizedCalendarDto> monthlyRecords = convertIntoDtoList(intakeRecord);

        return RecordGetWaterListResponseDto.from(monthlyRecords);
    }

    public List<CustomizedCalendarDto> convertIntoDtoList(List<IntakeRecord> targetList) {
        List<CustomizedCalendarDto> convertedList = new ArrayList<>();

        for (IntakeRecord model : targetList) {
            LocalDate recordTime = model.getRecordTime();
            Long totalSum = model.getTotalSum();

            CustomizedCalendarDto customizedDto = CustomizedCalendarDto.builder()
                    .recordTime(recordTime)
                    .totalSum(totalSum)
                    .build();

            convertedList.add(customizedDto);
        }

        return convertedList;
    }

    public boolean isEnough(User user, LocalDate dateTime){
        Integer yesterdayRecordSum = findDailyRecord(user, dateTime).getTotalSum();
        return yesterdayRecordSum >= user.getGoalAmount();
    }
}
