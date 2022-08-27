package com.wdw.wdw.repository;

import com.wdw.wdw.domain.Record;
import com.wdw.wdw.domain.RecordDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RecordRepository {

    @PersistenceContext
    private final EntityManager em;

    public void save(Record record) {
        em.persist(record);
    }

    public Record findOne(Long id) {
        return em.find(Record.class, id);
    }

    public List<Record> findAll(String name) {
        return em.createQuery("select r from Record r where r.user = :user", Record.class)
                .setParameter("user", name)
                .getResultList();
    }

    public List<Record> findRecordsByDay(LocalDateTime date, Long userId) {
        //오늘을 기준으로 조회하는 경우면 날짜로만 처리해야한다.
        return em.createQuery("select r from Record r where r.recordDate.year = :year and r.recordDate.month = :month and r.recordDate.day = :day and r.user.id = :userId order by r.recordDate.time", Record.class)
                .setParameter("year", date.getYear())
                .setParameter("month", date.getMonthValue())
                .setParameter("day", date.getDayOfMonth())
                .setParameter("userId", userId)
                .getResultList();
    }

    public List<Record> findRecordsByWeek(LocalDateTime date) {
        Calendar cal = Calendar.getInstance();
        int week;
        cal.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth());

        Calendar sday = (Calendar) cal.clone();
        week = sday.get(Calendar.DAY_OF_WEEK);
        sday.add(Calendar.DATE, -(week - 1));
//        LocalDate startDay = LocalDateTime.ofInstant(sday.toInstant(), ZoneId.systemDefault());
        LocalDate startDay = LocalDate.ofInstant(sday.toInstant(), ZoneId.systemDefault());


        Calendar eday = (Calendar) cal.clone();
        week = eday.get(Calendar.DAY_OF_WEEK);
        eday.add(Calendar.DATE, 7 - week);

        LocalDate endDay = LocalDate.ofInstant(sday.toInstant(), ZoneId.systemDefault());


        return em.createQuery("select r from Record r where r.recordDate.convertedDate between :startDay and :endDay order by r.recordDate.day")
                .setParameter("startDay", startDay)
                .setParameter("endDay", endDay)
                .getResultList();
    }

    public List<Record> findRecordsByMonth(LocalDateTime date) {
        LocalDate currentDate = LocalDate.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
        int currentMonth = currentDate.getMonthValue();
        RecordDate firstDayOfMonth = new RecordDate(date.getYear(), currentMonth, 1);

        return em.createQuery("select r from Record r where r.recordDate.convertedDate between :past and :cur order by r.recordDate.day")
                .setParameter("past", firstDayOfMonth)
                .setParameter("cur", date)
                .getResultList();
    }

}
