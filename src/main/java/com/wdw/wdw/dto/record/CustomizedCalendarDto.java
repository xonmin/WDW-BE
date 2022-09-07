package com.wdw.wdw.dto.record;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CustomizedCalendarDto {

    private LocalDate recordTime;
    private Long totalSum;

    @Builder
    public CustomizedCalendarDto(LocalDate recordTime, Long totalSum) {
        this.recordTime = recordTime;
        this.totalSum = totalSum;
    }
}
