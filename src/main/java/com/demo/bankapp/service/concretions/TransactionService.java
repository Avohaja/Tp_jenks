package com.demo.bankapp.service.concretions;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.bankapp.model.Transaction;
import com.demo.bankapp.repository.TransactionRepository;
import com.demo.bankapp.service.abstractions.ITransactionService;

@Service
public class TransactionService implements ITransactionService {

	private TransactionRepository repository;

	@Autowired
	public TransactionService(TransactionRepository repository) {
		this.repository = repository;
	}

	@Override
	public Transaction createNewTransaction(Long userId, boolean isBuying, String currency, BigDecimal amount) {
		Transaction transaction = new Transaction(userId, isBuying, currency, amount);
		return repository.save(transaction);
	}

	@Override
	public int getOperationCountFromLast24Hours(Long userId) {
		Date cutoff = new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000L);
		return repository.getOperationCountFromLast24Hours(userId, cutoff);
	}

	@Override
	public List<Transaction> findAllByUserId(Long userId) {
		return repository.findAllByUserId(userId);
	}

}
