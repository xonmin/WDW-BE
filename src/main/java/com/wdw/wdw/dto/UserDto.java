package com.wdw.wdw.dto;

import com.wdw.wdw.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserDto {

    @Builder @Getter
    @AllArgsConstructor
    public static class JoinReq{
        private String username;
        private String password;
        private String email;
        private String name;

        public User toEntity(){
            return User.builder()
                    .username(this.username)
                    .password(this.password)
                    .email(this.email)
                    .name(this.name)
                    .build();
        }
    }

    @Getter @Builder
    @AllArgsConstructor
    public static class LoginReq {
        private String username;
        private String password;
    }

    @Getter @Builder
    @AllArgsConstructor
    public static class UpdateReq {
        private String password;
        private String email;
        private String name;
        private Integer weight;
    }


}
