package org.shubnikofff.circuit_breaker;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;

public class CircuitBreaker {

	private State state = State.CLOSED;

	private final int failureThreshold;

	private final Duration resetTimeout;

	private final AtomicInteger failureCount = new AtomicInteger(0);

	private Instant lastFailureTime = Instant.now();

	public CircuitBreaker(int failureThreshold, Duration resetTimeout) {
		this.failureThreshold = failureThreshold;
		this.resetTimeout = resetTimeout;
	}

	public synchronized boolean allowOperation() {
		if (state == State.OPEN) {
			if(Duration.between(lastFailureTime, Instant.now()).compareTo(resetTimeout) > 0) {
				state = State.HALF_OPEN;
				return true;
			}

			return false;
		}

		return true;
	}

	public synchronized void recordSuccess() {
		if(state == State.HALF_OPEN) {
			state = State.CLOSED;
		}

		failureCount.set(0);
	}

	public synchronized void recordFailure() {
		final var fails = failureCount.incrementAndGet();
		lastFailureTime = Instant.now();
		if(state == State.HALF_OPEN && fails >= failureThreshold) {
			state = State.OPEN;
		}
	}

	private enum State {
		CLOSED,
		HALF_OPEN,
		OPEN
	}

}
