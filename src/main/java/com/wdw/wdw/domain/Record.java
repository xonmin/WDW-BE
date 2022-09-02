package com.wdw.wdw.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_seq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private User user;

    private Integer quantity;

    private LocalDate recordTime;

    @Builder
    public Record(
            Long id,
            User user,
            Integer quantity,
            LocalDate date)
    {
        this.id = id;
        this.user = user;
        this.quantity = quantity;
        this.recordTime = date;
    }


    public Record() {
        this.recordTime = LocalDate.now();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUsername() {
        return this.user.getUsername();
    }
}
