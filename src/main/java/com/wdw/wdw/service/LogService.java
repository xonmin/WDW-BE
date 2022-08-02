package com.wdw.wdw.service;

import com.wdw.wdw.domain.Log;
import com.wdw.wdw.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LogService {

    private final LogRepository logRepository;

    @Transactional
    public Long addLog(Log log) {
        //validation 필요한가
        logRepository.save(log);
        return log.getId();
    }

    public List<Log> findLogsByUsername(String name) {
        return logRepository.findAll(name);
    }
}
