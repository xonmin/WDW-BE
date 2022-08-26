package com.wdw.wdw.service;

import com.wdw.wdw.domain.Record;
import com.wdw.wdw.domain.User;
import com.wdw.wdw.repository.RecordRepository;
import com.wdw.wdw.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long addRecord(Record record) {
        User user = record.getUser();
        user.appendWaterIntake(record.getQuantity());
        if (isEnough(user, LocalDateTime.now())){
            user.appendConsecutiveDays(1);
        }
        userRepository.save(user);
        recordRepository.save(record);
        return record.getId();
    }

    public List<Record> findLogsByUsername(String name) {
        return recordRepository.findAll(name);
    }

    public List<Record> findRecordByDay(LocalDateTime datetime, Long userId) {
        return recordRepository.findRecordsByDay(datetime, userId);
    }

    public List<Record> findRecordByWeek(LocalDateTime dateTime) {
        return recordRepository.findRecordsByWeek(dateTime);
    }

    public List<Record> findRecordByMonth(LocalDateTime dateTime) {
        return recordRepository.findRecordsByMonth(dateTime);
    }

    public boolean isEnough(User user, LocalDateTime dateTime){
        List<Record> yesterdayRecordList = findRecordByDay(dateTime, user.getId());
        int sum = yesterdayRecordList.parallelStream()
                .mapToInt(Record::getQuantity)
                .sum();
        return sum >= user.getGoalAmount();
    }
}
