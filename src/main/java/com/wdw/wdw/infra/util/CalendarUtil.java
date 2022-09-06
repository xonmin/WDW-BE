package com.wdw.wdw.infra.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;

public interface CalendarUtil {

    static LocalDate getStartDayOfWeek(LocalDate date) {
        Calendar cal = Calendar.getInstance();
        int week;
        cal.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth());

        Calendar sday = (Calendar) cal.clone();
        week = sday.get(Calendar.DAY_OF_WEEK);
        sday.add(Calendar.DATE, -(week - 1));
        return LocalDate.ofInstant(sday.toInstant(), ZoneId.systemDefault());
    }

    static LocalDate getEndDayOfWeek(LocalDate date) {
        Calendar cal = Calendar.getInstance();
        int week;
        cal.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth());

        Calendar eday = (Calendar) cal.clone();
        week = eday.get(Calendar.DAY_OF_WEEK);
        eday.add(Calendar.DATE, 7 - week);
        return LocalDate.ofInstant(eday.toInstant(), ZoneId.systemDefault());
    }
}
