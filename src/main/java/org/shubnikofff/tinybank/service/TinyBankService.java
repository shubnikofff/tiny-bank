package org.shubnikofff.tinybank.service;

import org.shubnikofff.tinybank.dto.TransactionHistoryResponse;
import org.shubnikofff.tinybank.dto.TransactionRequest;
import org.shubnikofff.tinybank.dto.TransactionResponse;
import org.shubnikofff.tinybank.exception.InsufficientFundsException;
import org.shubnikofff.tinybank.model.Account;
import org.shubnikofff.tinybank.model.Transaction;
import org.shubnikofff.tinybank.model.Transaction.Direction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import static java.util.UUID.randomUUID;


@Service
public class TinyBankService {

	private final Account account = new Account(BigDecimal.ZERO); // Tiny Bank has only one account

	public TransactionResponse deposit(TransactionRequest request) {

		// From task description we are not expected to implement transactions/atomic operations

		final var transaction = createTransaction(Direction.IN, request.amount());
		final var newBalance = account.getBalance().add(transaction.amount());

		account.setBalance(newBalance);
		account.addToHistory(transaction);
		account.setBalanceFor(transaction.id(), newBalance);

		return new TransactionResponse(transaction, newBalance);
	}

	public TransactionResponse withdraw(TransactionRequest request) throws InsufficientFundsException {

		// From task description we are not expected to implement transactions/atomic operations

		if(account.getBalance().compareTo(request.amount()) < 0) {
			throw new InsufficientFundsException();
		}

		final var transaction = createTransaction(Direction.OUT, request.amount());
		final var newBalance = account.getBalance().subtract(transaction.amount());

		account.setBalance(newBalance);
		account.addToHistory(transaction);
		account.setBalanceFor(transaction.id(), newBalance);

		return new TransactionResponse(transaction, newBalance);
	}

	public BigDecimal getBalance() {
		return account.getBalance();
	}

	public TransactionHistoryResponse getTransactionHistory(Instant date) {
		final var transactions = account.getTransactionHistory()
			.stream()
			.sorted(Comparator.comparing(Transaction::createdAt))
			.filter(transaction -> transaction.createdAt().compareTo(date) <= 0)
			.toList();

		if(transactions.isEmpty()) {
			return new TransactionHistoryResponse(List.of(), BigDecimal.ZERO);
		}

		final var balance = account.getBalanceFor(transactions.getLast().id());

		return new TransactionHistoryResponse(transactions, balance);
	}

	private Transaction createTransaction(Direction direction, BigDecimal amount) {
		UUID transactionId = randomUUID();
		while(account.getTransaction(transactionId) != null) {
			transactionId = randomUUID();
		}

		return new Transaction(transactionId, direction, amount, Instant.now());
	}
}
