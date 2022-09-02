package com.wdw.wdw.repository;

import com.wdw.wdw.domain.Record;
import com.wdw.wdw.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RecordRepository extends JpaRepository<Record, Long> {

    List<Record> findRecordsByUser(User user);

    Optional<Record> findRecordById(Long id);

    @Query(value = "select sum(r.quantity) from Record r "
            + "where r.user = :user " +
            "and r.recordTime = :time")
    Integer findByDailyDate(
            @Param("user") User user,
            @Param("time") LocalDate targetTime
    );

    @Query(value = "select r from Record r " +
            "where r.user = :user " +
            "and r.recordTime between :startDay " +
            "and :endDay order by r.id")
    List<Record> findByWeeklyDate(
            @Param("user") User user,
            @Param("startDay") LocalDate startDay,
            @Param("endDay") LocalDate endDay
    );

    @Query(value = "select r from Record r " +
            "where r.user = :user " +
            "and r.recordTime between :past " +
            "and :cur order by r.id")
    List<Record> findByMonthlyDate(
            @Param("user") User user,
            @Param("past") LocalDate past,
            @Param("cur") LocalDate cur
    );

}
