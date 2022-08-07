package com.wdw.wdw.repository;

import com.wdw.wdw.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User getOne(Long id);
}
