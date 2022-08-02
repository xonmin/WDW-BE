package com.wdw.wdw.repository;

import com.wdw.wdw.domain.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class LogRepository {

    @PersistenceContext
    private final EntityManager em;

    public void save(Log log) {
        em.persist(log);
    }

    public Log findOne(Long id) {
        return em.find(Log.class, id);
    }

    public List<Log> findAll(String name) {
        return em.createQuery("select l from Log l where l.user = :user", Log.class)
                .setParameter("user", name)
                .getResultList();
    }

}
