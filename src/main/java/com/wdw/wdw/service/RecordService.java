package com.wdw.wdw.service;

import com.wdw.wdw.domain.Record;
import com.wdw.wdw.domain.User;
import com.wdw.wdw.dto.record.RecordAddRequestDto;
import com.wdw.wdw.dto.record.RecordAddResponseDto;
import com.wdw.wdw.dto.record.RecordGetResponseListDto;
import com.wdw.wdw.dto.record.RecordGetResponseValueDto;
import com.wdw.wdw.repository.RecordRepository;
import com.wdw.wdw.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
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

    public RecordGetResponseValueDto findDailyRecord(User user, LocalDate date) {
        Integer rtnValue = recordRepository.findByDailyDate(user, date);
        return RecordGetResponseValueDto.from(rtnValue);
    }


    public RecordGetResponseListDto findWeeklyRecord(User user, LocalDate date) {
        Calendar cal = Calendar.getInstance();
        int week;
        cal.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth());

        Calendar sday = (Calendar) cal.clone();
        week = sday.get(Calendar.DAY_OF_WEEK);
        sday.add(Calendar.DATE, -(week - 1));
        LocalDate startDay = LocalDate.ofInstant(sday.toInstant(), ZoneId.systemDefault());

        Calendar eday = (Calendar) cal.clone();
        week = eday.get(Calendar.DAY_OF_WEEK);
        eday.add(Calendar.DATE, 7 - week);
        LocalDate endDay = LocalDate.ofInstant(sday.toInstant(), ZoneId.systemDefault());

        List<Record> rtnList = recordRepository.findByWeeklyDate(user, startDay, endDay);

        return RecordGetResponseListDto.from(rtnList);
    }

    public RecordGetResponseListDto findMonthlyRecord(User user, LocalDate date) {
        int curMonth = date.getMonthValue();
        LocalDate startDay = LocalDate.of(date.getYear(), curMonth, 1);

        List<Record> rtnList = recordRepository.findByMonthlyDate(user, startDay, date);

        return RecordGetResponseListDto.from(rtnList);
    }

    public boolean isEnough(User user, LocalDate dateTime){
        Integer yesterdayRecordSum = findDailyRecord(user, dateTime).getTotalSum();
        return yesterdayRecordSum >= user.getGoalAmount();
    }
}
