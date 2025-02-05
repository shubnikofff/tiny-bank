package org.shubnikofff.cache;

import java.math.BigDecimal;
import java.util.Currency;

// each query to getRate cost us 0.01$ and have 98% availability
public interface RateService {

	BigDecimal getRate(Currency from, Currency to);

}
