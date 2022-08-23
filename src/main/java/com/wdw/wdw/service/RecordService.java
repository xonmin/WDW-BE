package com.wdw.wdw.service;

import com.wdw.wdw.domain.Record;
import com.wdw.wdw.repository.RecordRepository;
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

    @Transactional
    public Long addRecord(Record record) {
        //validation 필요한가
        record.getUser().setWaterIntake(record.getQuantity());
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
}
