package com.wdw.wdw.repository;

import com.wdw.wdw.domain.Record;
import com.wdw.wdw.domain.User;
import com.wdw.wdw.dto.record.IntakeRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Long> {

    @Query(value = "select sum(r.quantity) from Record r "
            + "where r.user = :user " +
            "and r.recordTime = :time")
    Integer findQuantity(
            @Param("user") User user,
            @Param("time") LocalDate targetTime
    );

    @Query(value = "select new com.wdw.wdw.dto.record.IntakeRecord(r.recordTime, sum(r.quantity)) from Record r " +
            "where r.user = :user and r.recordTime " +
            "between :start and :end " +
            "group by r.recordTime " +
            "order by r.recordTime")
    List<IntakeRecord> findPastRecords(
            @Param("user") User user,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );

}
