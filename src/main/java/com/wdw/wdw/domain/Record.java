package com.wdw.wdw.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
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

    public Record(int quantity) {
        this.quantity = quantity;
        this.recordDate = new RecordDate(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth(), LocalDateTime.now());
    }

    public void setUser(User user) {
        this.user = user;
        user.getRecords().add(this);
    }
}
