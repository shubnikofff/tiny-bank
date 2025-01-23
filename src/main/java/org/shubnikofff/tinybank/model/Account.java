package org.shubnikofff.tinybank.model;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class Account {

	private BigDecimal balance;

	private final List<Transaction> transactionHistory = new LinkedList<>();

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
		return List.copyOf(transactionHistory);
	}

	public void addToHistory(Transaction transaction) {
		transactionHistory.add(transaction);
	}
}
