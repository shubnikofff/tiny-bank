package org.shubnikofff.cache;

import java.math.BigDecimal;
import java.util.Currency;

public class CurrencyConverter {

	public static final long CACHE_TTL = 1000;

	private final RateService rateService;

	private final CacheService<String, BigDecimal> cacheService;

	public CurrencyConverter(RateService rateService, CacheService<String, BigDecimal> cacheService) {
		this.rateService = rateService;
		this.cacheService = cacheService;
	}

	// this function will be called 1000 times per second
	public BigDecimal convert(Currency from, Currency to) {
		final var key = generateKey(from, to);

		return cacheService.get(key).orElseGet(() -> {
			final var rate = rateService.getRate(from, to);
			cacheService.put(key, rate, CACHE_TTL);
			return rate;
		});
	}

	private static String generateKey(Currency from, Currency to) {
		return "%s#%s".formatted(from, to);
	}
}
