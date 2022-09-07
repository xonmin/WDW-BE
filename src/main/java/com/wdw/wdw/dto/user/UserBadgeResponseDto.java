package com.wdw.wdw.dto.user;

import com.wdw.wdw.domain.Badge;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class UserBadgeResponseDto {

    private List<Badge> badgeList;

    @Builder
    public UserBadgeResponseDto(List<Badge> badgeList) {
        this.badgeList = badgeList;
    }

    public static UserBadgeResponseDto from(List<Badge> badges) {
        return UserBadgeResponseDto.builder()
                .badgeList(badges)
                .build();
    }
}
