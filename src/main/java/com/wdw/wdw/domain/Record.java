package com.wdw.wdw.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_seq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private User user;

    @Embedded
    private RecordDate recordDate;
    private int quantity;

    public Record() {
        this.recordDate = new RecordDate(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());
    }

    public Record(int quantity) {
        this.quantity = quantity;
        this.recordDate = new RecordDate(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());
    }

    public void setUser(User user) {
        this.user = user;
    }
}
