package org.shubnikofff.tinybank.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransactionRequest(
	@NotNull(message = "Transaction amount is required")
	@DecimalMin(value = "0.01", message = "Transaction amount must be at least 0.01")
	@DecimalMax(value = "1000000.00", message = "Transaction amount must not exceed 1,000,000.00")
	@Digits(integer = 10, fraction = 2, message = "Transaction amount must have up to 10 digits and 2 decimal places")
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "10.42")
	BigDecimal amount
) {

}
