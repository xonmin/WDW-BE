package com.wdw.wdw.dto.record;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class RecordGetDailyWaterResponseDto {

    private LocalDate recordDate;
    private Integer totalSum;

    @Builder
    public RecordGetDailyWaterResponseDto(LocalDate recordDate, Integer totalSum) {
        this.recordDate = recordDate;
        this.totalSum = totalSum;
    }

    public static RecordGetDailyWaterResponseDto from(LocalDate date, Integer sumAmount) {
        return RecordGetDailyWaterResponseDto.builder()
                .recordDate(date)
                .totalSum(sumAmount)
                .build();
    }

}
