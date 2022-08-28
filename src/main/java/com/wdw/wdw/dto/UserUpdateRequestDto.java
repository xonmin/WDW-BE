package com.wdw.wdw.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserUpdateRequestDto {

    private String password;

    private String name;

    private Integer weight;
}
