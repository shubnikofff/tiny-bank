package org.shubnikofff.tinybank.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.shubnikofff.tinybank.dto.TransactionRequest;
import org.shubnikofff.tinybank.dto.TransactionResponse;
import org.shubnikofff.tinybank.exception.InsufficientFundsException;
import org.shubnikofff.tinybank.model.Transaction;
import org.shubnikofff.tinybank.service.TinyBankService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@Tag(name = "Bank Controller", description = "Bank operations")
@RequestMapping("api/v1")
public class BankController {

	private final TinyBankService bankService;

	public BankController(TinyBankService bankService) {
		this.bankService = bankService;
	}

	@PostMapping("deposit")
	TransactionResponse deposit(@Valid @RequestBody TransactionRequest request) {
		return bankService.deposit(request);
	}

	@PostMapping("withdraw")
	ResponseEntity<TransactionResponse> withdraw(@Valid @RequestBody TransactionRequest request) {
		try {
			return ResponseEntity.ok(bankService.withdraw(request));
		} catch (InsufficientFundsException e) {
			// Simply return 400, also @ControllerAdvice can be used
			return ResponseEntity.badRequest().build();
		}
	}

	@GetMapping("balance")
	@Operation(description = "Deposit account")
	BigDecimal getBalance() {
		return bankService.getBalance();
	}

	@GetMapping("transaction-history")
	@Operation(description = "Deposit account")
	List<Transaction> getTransactionHistory() {
		return bankService.getTransactionHistory();
	}

}
