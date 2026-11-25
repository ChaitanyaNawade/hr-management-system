package com.aarambh.hr_management.repository;

import com.aarambh.hr_management.entity.User;
import org.hibernate.dialect.lock.OptimisticEntityLockException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long>
{
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
