package com.UniTech.UniTechTest.repository;

import com.UniTech.UniTechTest.model.TransferHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferRepository extends JpaRepository<TransferHistory, Long> {
}
