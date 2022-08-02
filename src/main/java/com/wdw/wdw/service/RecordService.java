package com.wdw.wdw.service;

import com.wdw.wdw.domain.Record;
import com.wdw.wdw.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;

    @Transactional
    public Long addLog(Record record) {
        //validation 필요한가
        recordRepository.save(record);
        return record.getId();
    }

    public List<Record> findLogsByUsername(String name) {
        return recordRepository.findAll(name);
    }
}
