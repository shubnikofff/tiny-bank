package org.shubnikofff.tinybank.dto;

import org.shubnikofff.tinybank.model.Transaction;

import java.math.BigDecimal;

public record TransactionResponse(Transaction transaction, BigDecimal balance) {

}
