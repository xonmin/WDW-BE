package com.wdw.wdw.controller;


import com.wdw.wdw.dto.UserDto;
import com.wdw.wdw.infra.jwt.PrincipalDetails;
import com.wdw.wdw.domain.User;
import com.wdw.wdw.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    User join(@RequestBody UserDto.JoinReq req){
        log.info("회원가입");
        return userService.signUp(req);
    }

    @PutMapping("/user/update")
    User update(@AuthenticationPrincipal PrincipalDetails details, @RequestBody UserDto.UpdateReq req) {
        return userService.update(details.getUsername(), req);
    }

    @GetMapping("/user")
    User user(@AuthenticationPrincipal PrincipalDetails details) {
        return details.getUser();
    }

    @PostMapping("/user/test")
    User test(Authentication authentication){
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        System.out.println("principal.getUser() = " + principal.getUser());
        System.out.println("로그인 정보");
        return principal.getUser();
    }

}
