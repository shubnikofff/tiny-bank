package org.shubnikofff.rate_limiter;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class TokenBucketRateLimiter implements RateLimiter {

	private final int maxTokens;

	private final long refillIntervalMs;

	private final AtomicInteger tokens;

	private volatile long lastRefillTimestamp = System.currentTimeMillis();

	public TokenBucketRateLimiter(int maxTokens, long refillIntervalMs) {
		this.maxTokens = maxTokens;
		this.refillIntervalMs = refillIntervalMs;
		tokens = new AtomicInteger(maxTokens);
	}


//	public TokenBucketRateLimiter(int tokensThreshold, long windowMs) {
//		this.tokensThreshold = tokensThreshold;
//		this.windowMs = windowMs;
//	}

	@Override
	public boolean tryAcquire() {

//		final var now = System.currentTimeMillis();
//
//		final var tokensToAdd = (now - lastRefillTimestamp) / windowMs * tokensThreshold;
//
//		if(tokensToAdd > 0) {
//			tokens.set(Math.min(tokensToAdd, tokensThreshold));
//			lastRefillTimestamp = System.currentTimeMillis();
//		}
//
//		if(tokens.get() > 0) {
//			tokens.decrementAndGet();
//			return true;
//		}

		final var now = System.currentTimeMillis();
		final var elapsedTime = now - lastRefillTimestamp;

		if(elapsedTime >= refillIntervalMs) {
			final var tokenToAdd = (int) (elapsedTime / refillIntervalMs);
			tokens.set(Math.min(tokens.get() + tokenToAdd, maxTokens));
			lastRefillTimestamp = now;
		}

		if (tokens.get() > 0) {
			tokens.decrementAndGet();
			return true;
		}

		return false;
	}
}
