package com.bankonline.Final_Project.repositories.transactions;

import com.bankonline.Final_Project.models.transactions.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query(value = "SELECT max(time) FROM transaction AS trans WHERE trans.account_id = ?1",nativeQuery = true)
    Optional<LocalDateTime> findByUserIdLast(Long userId);

    @Query(value = "SELECT max(amount) from transaction as trans where trans.account_id = ?1",nativeQuery = true)
    Optional<BigDecimal> max(Long countID);
}
