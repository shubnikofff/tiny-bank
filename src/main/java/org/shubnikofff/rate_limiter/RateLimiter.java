package org.shubnikofff.rate_limiter;

public interface RateLimiter {

	boolean tryAcquire();

}
