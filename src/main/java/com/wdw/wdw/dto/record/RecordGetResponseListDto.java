package com.wdw.wdw.dto.record;

import com.wdw.wdw.domain.Record;
import lombok.Builder;

import java.util.List;

public class RecordGetResponseListDto {

    List<Record> records;

    @Builder
    public RecordGetResponseListDto(List<Record> records) {
        this.records = records;
    }

    public static RecordGetResponseListDto from(List<Record> records) {
        return RecordGetResponseListDto.builder()
                .records(records)
                .build();
    }
}
