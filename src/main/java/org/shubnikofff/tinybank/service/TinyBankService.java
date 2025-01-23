package org.shubnikofff.tinybank.service;

import org.shubnikofff.tinybank.dto.TransactionRequest;
import org.shubnikofff.tinybank.dto.TransactionResponse;
import org.shubnikofff.tinybank.exception.InsufficientFundsException;
import org.shubnikofff.tinybank.model.Account;
import org.shubnikofff.tinybank.model.Transaction;
import org.shubnikofff.tinybank.model.Transaction.Direction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static java.util.UUID.randomUUID;


@Service
public class TinyBankService {

	private final Account account = new Account(BigDecimal.ZERO); // Tiny Bank has only one account

	public TransactionResponse deposit(TransactionRequest request) {
		final var transaction = new Transaction(randomUUID(), Direction.IN, request.amount(), Instant.now());
		final var newBalance = account.getBalance().add(transaction.amount());

		// From task description we are not expected to implement transactions/atomic operations
		account.setBalance(newBalance);
		account.addToHistory(transaction);

		return new TransactionResponse(transaction, newBalance);
	}

	public TransactionResponse withdraw(TransactionRequest request) throws InsufficientFundsException {
		if(account.getBalance().compareTo(request.amount()) < 0) {
			throw new InsufficientFundsException();
		}

		final var transaction = new Transaction(randomUUID(), Direction.OUT, request.amount(), Instant.now());
		final var newBalance = account.getBalance().subtract(transaction.amount());

		// From task description we are not expected to implement transactions/atomic operations
		account.setBalance(newBalance);
		account.addToHistory(transaction);

		return new TransactionResponse(transaction, newBalance);
	}

	public BigDecimal getBalance() {
		return account.getBalance();
	}

	public List<Transaction> getTransactionHistory() {
		return  account.getTransactionHistory();
	}
}
