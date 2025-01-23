package org.shubnikofff.tinybank.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;


public record Transaction(
	UUID id,
	Direction direction,
	BigDecimal amount,
	Instant createdAt
) {
	public enum Direction {
		IN,
		OUT
	}
}
