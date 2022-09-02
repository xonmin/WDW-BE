package com.wdw.wdw.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserJoinRequestDto {

    private String username;

    private String password;

    private String name;
}
