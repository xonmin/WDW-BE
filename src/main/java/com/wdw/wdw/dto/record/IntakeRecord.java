package com.wdw.wdw.dto.record;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class IntakeRecord implements Serializable {

    private LocalDate recordTime;
    private Long totalSum;

    public IntakeRecord(LocalDate recordTime, Long totalSum) {
        this.recordTime = recordTime;
        this.totalSum = totalSum;
    }
}
