package com.wdw.wdw.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_seq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private LocalDateTime date;
    private int quantity;
}
