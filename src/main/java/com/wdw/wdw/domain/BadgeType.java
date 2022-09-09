package com.wdw.wdw.domain;

import lombok.Getter;

@Getter
public enum BadgeType {
    SUM_BASIC(0, null),
    SUM_BRONZE(10000, null), // 10L
    SUM_SILVER(50000, null), // 50L
    SUM_GOLD(100000, null),   // 100L
    CON_BASIC(null, 0),
    CON_BRONZE(null, 7), // 1week
    CON_SILVER(null, 30), // 1month
    CON_GOLD(null, 180);   // 6month

    private Integer intakeOfSum;
    private Integer consecutiveDay;

    BadgeType(Integer intakeOfSum, Integer consecutiveDay) {
    }
}
