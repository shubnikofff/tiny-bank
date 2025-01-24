package org.shubnikofff.tinybank.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Account {

	private BigDecimal balance;

	private final Map<UUID, Transaction> transactionHistory = new HashMap<>();

	public Account(BigDecimal initialBalance) {
		balance = initialBalance;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public List<Transaction> getTransactionHistory() {
		return List.copyOf(transactionHistory.values());
	}

	public void addToHistory(Transaction transaction) {
		transactionHistory.put(transaction.id(), transaction);
	}

	public Transaction getTransaction(UUID transactionId) {
		return transactionHistory.get(transactionId);
	}
}
