package com.wdw.wdw.domain;

import lombok.Getter;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
@Getter
public class RecordDate {

    private int year;
    private int month;
    private int day;
    private LocalDateTime time;

    protected RecordDate() {
        //java spec상 만들어놓음
    }

    public RecordDate(int year, int month, int day, LocalDateTime time) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.time = time;
    }
}
