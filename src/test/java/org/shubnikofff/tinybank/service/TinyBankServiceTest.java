package org.shubnikofff.tinybank.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.shubnikofff.tinybank.dto.TransactionRequest;
import org.shubnikofff.tinybank.exception.InsufficientFundsException;
import org.shubnikofff.tinybank.model.Transaction.Direction;

import java.math.BigDecimal;
import java.time.Instant;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


class TinyBankServiceTest {

	private TinyBankService tinyBankService;

	@BeforeEach
	void setUp() {
		tinyBankService = new TinyBankService();
	}

	@Test
	void should_update_balance_and_transaction_history_when_deposit_account_test() {
		final var transactionAmount = BigDecimal.valueOf(100.00);
		final var expectedBalance = BigDecimal.valueOf(100.00);
		final var transactionResponse = tinyBankService.deposit(new TransactionRequest(transactionAmount));
		assertThat(transactionResponse.transaction().amount()).isEqualTo(transactionAmount);
		assertThat(transactionResponse.transaction().direction()).isEqualTo(Direction.IN);
		assertThat(transactionResponse.balance()).isEqualTo(expectedBalance);

		final var balance = tinyBankService.getBalance();
		assertThat(balance).isEqualTo(expectedBalance);

		final var transactionHistory = tinyBankService.getTransactionHistory(Instant.now());
		assertThat(transactionHistory.history()).hasSize(1).contains(transactionResponse.transaction());
	}

	@Test
	void should_update_balance_and_transaction_history_when_withdraw_test() throws InsufficientFundsException {
		// before withdraw we should deposit account because initially it has zero balance
		tinyBankService.deposit(new TransactionRequest(BigDecimal.valueOf(100.00)));

		final var transactionAmount = BigDecimal.valueOf(58.42);
		final var expectedBalance = BigDecimal.valueOf(41.58);
		final var transactionResponse = tinyBankService.withdraw(new TransactionRequest(transactionAmount));
		assertThat(transactionResponse.transaction().amount()).isEqualTo(transactionAmount);
		assertThat(transactionResponse.transaction().direction()).isEqualTo(Direction.OUT);
		assertThat(transactionResponse.balance()).isEqualTo(expectedBalance);

		final var balance = tinyBankService.getBalance();
		assertThat(balance).isEqualTo(expectedBalance);

		final var transactionHistory = tinyBankService.getTransactionHistory(Instant.now());
		assertThat(transactionHistory.history()).hasSize(2).contains(transactionResponse.transaction());
	}

	@Test
	void should_throw_insufficient_funds_exception_test() {
		assertThatThrownBy(() -> tinyBankService.withdraw(new TransactionRequest(BigDecimal.TEN)))
			.isInstanceOf(InsufficientFundsException.class);
	}
}
