package com.wdw.wdw.dto.record;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class RecordGetWaterListResponseDto {

    private List<CustomizedCalendarDto> records;

    @Builder
    public RecordGetWaterListResponseDto(List<CustomizedCalendarDto> records) {
        this.records = records;
    }

    public static RecordGetWaterListResponseDto from(List<CustomizedCalendarDto> records) {
        return RecordGetWaterListResponseDto.builder()
                .records(records)
                .build();
    }
}
