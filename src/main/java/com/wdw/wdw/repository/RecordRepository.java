package com.wdw.wdw.repository;

import com.wdw.wdw.domain.Record;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    public List<Record> findRecordsByDay(LocalDateTime date) {
        //오늘을 기준으로 조회하는 경우면 날짜로만 처리해야한다.
        return em.createQuery("select r from Record r where r.recordDate.year = :year and r.recordDate.month = :month and r.recordDate.day = :day order by r.recordDate.time", Record.class)
                .setParameter("year", date.getYear())
                .setParameter("month", date.getMonthValue())
                .setParameter("day", date.getDayOfMonth())
                .getResultList();
    }

    public List<Record> findRecordsByWeek(LocalDateTime date) {
        return new ArrayList<>();
    }

    public List<Record> findRecordsByMonth(LocalDateTime date) {
        return new ArrayList<>();
    }

}
