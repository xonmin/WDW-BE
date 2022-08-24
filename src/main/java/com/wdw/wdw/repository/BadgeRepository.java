package com.wdw.wdw.repository;

import com.wdw.wdw.domain.Badge;
import com.wdw.wdw.domain.BadgeType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BadgeRepository extends JpaRepository<Badge, Long> {
    Optional<Badge> findByBadgeType(BadgeType type);
}
