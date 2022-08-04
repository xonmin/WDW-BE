package com.wdw.wdw.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
@Getter
public class Date {

    private int year;
    private int month;
    private int day;
    private LocalDateTime time;

    protected Date() {
        //java spec상 만들어놓음
    }

    public Date(int year, int month, int day, LocalDateTime time) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.time = time;
    }
}
