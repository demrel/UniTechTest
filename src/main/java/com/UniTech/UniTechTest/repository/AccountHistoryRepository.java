package com.UniTech.UniTechTest.repository;

import com.UniTech.UniTechTest.model.AccountHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountHistoryRepository  extends JpaRepository<AccountHistory, Long> {
}
