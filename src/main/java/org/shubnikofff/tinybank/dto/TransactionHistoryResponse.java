package org.shubnikofff.tinybank.dto;

import org.shubnikofff.tinybank.model.Transaction;

import java.math.BigDecimal;
import java.util.List;

public record TransactionHistoryResponse(
	List<Transaction> history,
	BigDecimal balance
) {

}
