package com.wdw.wdw.repository;

import com.wdw.wdw.domain.Record;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

}
