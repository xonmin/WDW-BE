package com.wdw.wdw.controller;


import com.wdw.wdw.dto.user.UserExistResponseDto;
import com.wdw.wdw.dto.user.UserGetResponseDto;
import com.wdw.wdw.dto.user.UserJoinRequestDto;
import com.wdw.wdw.dto.user.UserJoinResponseDto;
import com.wdw.wdw.dto.user.UserUpdateRequestDto;
import com.wdw.wdw.dto.user.UserUpdateResponseDto;
import com.wdw.wdw.infra.ApiResponse;
import com.wdw.wdw.infra.jwt.PrincipalDetails;
import com.wdw.wdw.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ApiResponse<UserJoinResponseDto> join(@RequestBody UserJoinRequestDto req){
        log.info("회원가입");
        return ApiResponse.success(HttpStatus.OK, userService.signUp(req));
    }

    @GetMapping("/exist")
    public ApiResponse<UserExistResponseDto> exist(@RequestParam String username) {
        return ApiResponse.success(HttpStatus.OK, userService.validateUsername(username));
    }

    @PutMapping("/user/update")
    public ApiResponse<UserUpdateResponseDto> update(@AuthenticationPrincipal PrincipalDetails details, @RequestBody UserUpdateRequestDto req) {
        return ApiResponse.success(HttpStatus.OK, userService.update(details.getUsername(), req));
    }

    @GetMapping("/user")
    public ApiResponse<UserGetResponseDto> user(@AuthenticationPrincipal PrincipalDetails details) {
        return ApiResponse.success(HttpStatus.OK, UserGetResponseDto.from(details.getUser()));
    }
}
