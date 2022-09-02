package com.wdw.wdw.dto.record;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RecordGetResponseValueDto {

    Integer totalSum;

    @Builder
    public RecordGetResponseValueDto(Integer totalSum) {
        this.totalSum = totalSum;
    }

    public static RecordGetResponseValueDto from(Integer sumAmount) {
        return RecordGetResponseValueDto.builder()
                .totalSum(sumAmount)
                .build();
    }
}
