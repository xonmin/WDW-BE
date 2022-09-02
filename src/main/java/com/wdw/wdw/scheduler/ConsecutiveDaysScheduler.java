package com.wdw.wdw.scheduler;

import com.wdw.wdw.domain.User;
import com.wdw.wdw.repository.UserRepository;
import com.wdw.wdw.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Component
public class ConsecutiveDaysScheduler {

    private final UserRepository userRepository;

    private final RecordService recordService;

    @Async
    @Scheduled(cron = "0 5 0 * * *")
    public void updateConsecutiveDays() {
        List<User> userList = userRepository.findAll();
        LocalDate yesterday = LocalDate.now().minusDays(1);
        userList.parallelStream()
                .filter(user -> !recordService.isEnough(user, yesterday))
                .forEach(user -> user.setConsecutiveDays(0));
    }
}
