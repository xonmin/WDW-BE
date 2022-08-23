package com.wdw.wdw.service;

import com.wdw.wdw.domain.Record;
import com.wdw.wdw.domain.User;
import com.wdw.wdw.dto.UserDto;
import com.wdw.wdw.dto.UserDto.UpdateReq;
import com.wdw.wdw.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final BCryptPasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public User signUp(UserDto.JoinReq req) {
        log.info("service signUp");
        User user = User.builder()
                .username(req.getUsername())
                .password(passwordEncoder.encode(req.getPassword()))
                .email(req.getEmail())
                .name(req.getName())
                .roles("ROLE_USER")
                .build();
        userRepository.save(user);
        return user;
    }

    public User update(String username, UpdateReq req) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(EntityNotFoundException::new);
        user.userUpdate(req);
        userRepository.save(user);
        return user;
    }

}
