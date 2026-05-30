package com.demo.bankapp.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.demo.bankapp.model.Transaction;

@RepositoryRestResource(exported = false)
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	@Query(value = "SELECT COUNT(*) FROM Transaction WHERE userId = :userId and transactionTime >= :cutoff")
	int getOperationCountFromLast24Hours(@Param("userId") Long userId, @Param("cutoff") Date cutoff);
	
	List<Transaction> findAllByUserId(Long userId);

}
