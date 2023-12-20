package com.UniTech.UniTechTest.repository;

import com.UniTech.UniTechTest.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AccountRepository  extends JpaRepository<Account, Long> {
    List<Account> findAllByIsActiveTrueAndUserId(Long userId);
}
