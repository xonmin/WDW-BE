package com.wdw.wdw.controller;


import com.wdw.wdw.domain.User;
import com.wdw.wdw.dto.UserDto;
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
    public ApiResponse<User> join(@RequestBody UserDto.JoinReq req){
        log.info("회원가입");
        return ApiResponse.success(HttpStatus.OK, userService.signUp(req));
    }

    @GetMapping("/exist")
    public ApiResponse<UserDto.ExistRes> exist(@RequestParam String username) {
        return ApiResponse.success(HttpStatus.OK, userService.validateUsername(username));
    }

    @PutMapping("/user/update")
    public ApiResponse<User> update(@AuthenticationPrincipal PrincipalDetails details, @RequestBody UserDto.UpdateReq req) {
        return ApiResponse.success(HttpStatus.OK, userService.update(details.getUsername(), req));
    }

    @GetMapping("/user")
    public ApiResponse<User> user(@AuthenticationPrincipal PrincipalDetails details) {
        return ApiResponse.success(HttpStatus.OK, details.getUser());
    }
}
