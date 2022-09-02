package com.wdw.wdw.dto.user;

import com.wdw.wdw.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserUpdateResponseDto {

    private String name;

    private Integer weight;

    private Integer consecutiveDays;

    private Integer waterIntake;

    private String updateDone;

    @Builder
    public UserUpdateResponseDto(String name, Integer weight, Integer consecutiveDays, Integer waterIntake, String updateDone) {
        this.name = name;
        this.weight = weight;
        this.consecutiveDays = consecutiveDays;
        this.waterIntake = waterIntake;
        this.updateDone = updateDone;
    }

    public static UserUpdateResponseDto from(User user, String updateDone) {
        return UserUpdateResponseDto.builder()
                .name(user.getName())
                .weight(user.getWeight())
                .consecutiveDays(user.getConsecutiveDays())
                .waterIntake(user.getWaterIntake())
                .updateDone(updateDone)
                .build();
    }

}
