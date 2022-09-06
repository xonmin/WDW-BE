package com.wdw.wdw.dto.record;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CustomizedCalendarDto {

    LocalDate recordTime;
    Long totalSum;

    @Builder
    public CustomizedCalendarDto(LocalDate recordTime, Long totalSum) {
        this.recordTime = recordTime;
        this.totalSum = totalSum;
    }
}
