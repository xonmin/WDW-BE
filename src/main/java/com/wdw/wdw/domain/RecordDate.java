package com.wdw.wdw.domain;

import lombok.Getter;

import javax.persistence.Embeddable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Embeddable
@Getter
public class RecordDate {

    private int year;
    private int month;
    private int day;
    private LocalDateTime time;

    private LocalDate convertedDate;

    protected RecordDate() {
        //java spec상 만들어놓음
    }

    public LocalDate convertToLocalDate() {
        return LocalDate.of(year, month, day);
    }

    public RecordDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.time = LocalDateTime.now();
        this.convertedDate = convertToLocalDate();
    }
}
