package com.wdw.wdw.dto.user;

import com.wdw.wdw.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserJoinResponseDto {

    private Long id;

    private String username;

    private String name;

    @Builder
    public UserJoinResponseDto(Long id, String username, String name) {
        this.id = id;
        this.username = username;
        this.name = name;
    }

    public static UserJoinResponseDto from (User user){
        return UserJoinResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .build();
    }
}
