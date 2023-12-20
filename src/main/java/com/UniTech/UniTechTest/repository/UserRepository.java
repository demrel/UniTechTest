package com.UniTech.UniTechTest.repository;

import com.UniTech.UniTechTest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
     Optional<User> findByPin(String pin);
     boolean existsByPin(String pin);

}
