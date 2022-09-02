package com.wdw.wdw.service;

import com.wdw.wdw.domain.User;
import com.wdw.wdw.dto.user.UserExistResponseDto;
import com.wdw.wdw.dto.user.UserJoinRequestDto;
import com.wdw.wdw.dto.user.UserJoinResponseDto;
import com.wdw.wdw.dto.user.UserUpdateRequestDto;
import com.wdw.wdw.dto.user.UserUpdateResponseDto;
import com.wdw.wdw.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final BCryptPasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public UserJoinResponseDto signUp(UserJoinRequestDto req) {
        User user = User.builder()
                .username(req.getUsername())
                .password(passwordEncoder.encode(req.getPassword()))
                .name(req.getName())
                .roles("ROLE_USER")
                .consecutiveDays(0)
                .waterIntake(0)
                .build();
        userRepository.save(user);
        return UserJoinResponseDto.from(user);
    }

    public UserUpdateResponseDto update(String username, UserUpdateRequestDto req) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(EntityNotFoundException::new);
        user.userUpdate(req);
        userRepository.save(user);
        return UserUpdateResponseDto.from(user, "updated");
    }

    public UserExistResponseDto validateUsername(String username) {
        return userRepository.findByUsername(username)
                .map(user -> new UserExistResponseDto("unusable"))
                .orElse(new UserExistResponseDto("usable"));
    }
}
