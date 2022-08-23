package com.wdw.wdw.repository;

import com.wdw.wdw.domain.Record;
import com.wdw.wdw.domain.RecordDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
        //수정 중
        LocalDate now = LocalDate.now();

        Calendar cal = Calendar.getInstance();

        cal.set(now.getYear(), now.getMonthValue()-1, now.getDayOfMonth());
        cal.setFirstDayOfWeek(Calendar.SUNDAY);
        int weekYear = cal.get(Calendar.WEEK_OF_YEAR);

        LocalDate currentDate = LocalDate.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth());
        LocalDate pastDay = currentDate.minusWeeks(1);


        return em.createQuery("select r from Record r where r.recordDate.convertedDate between :past and :cur order by r.recordDate.day")
                .setParameter("past", pastDay)
                .setParameter("cur", now)
                .getResultList();
    }

    public List<Record> findRecordsByMonth(LocalDateTime date) {
        LocalDate now = LocalDate.now();
        RecordDate recordDate;
        LocalDate currentDate = LocalDate.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth());
        int currentMonth = currentDate.getMonthValue();
        RecordDate firstDayOfMonth = new RecordDate(now.getYear(), currentMonth, 1);
//        LocalDate pastDay = currentDate.minusMonths(1);


        return em.createQuery("select r from Record r where r.recordDate.convertedDate between :past and :cur order by r.recordDate.day")
                .setParameter("past", firstDayOfMonth)
                .setParameter("cur", now)
                .getResultList();
    }

}
