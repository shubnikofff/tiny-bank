package org.shubnikofff.cache;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class InMemoryCacheService<K, V> implements CacheService<K, V> {

	private final ConcurrentMap<K, CacheEntry<V>> cache = new ConcurrentHashMap<>();

	@Override
	public void put(K key, V value, long ttlMs) {
		if(key == null || value == null || ttlMs <= 0) {
			throw new IllegalArgumentException();
		}

		cache.put(key, new CacheEntry<>(value, System.currentTimeMillis() + ttlMs));
	}

	@Override
	public Optional<V> get(K key) {
		if(key == null) {
			throw  new IllegalArgumentException();
		}

		final var entry = cache.get(key);

		if(entry == null) {
			return Optional.empty();
		}

		final var currentTimeMillis = System.currentTimeMillis();
		if(entry.ttl < currentTimeMillis) {
			cache.remove(key);
			return Optional.empty();
		}

		return Optional.of(entry.value);
	}

	private record CacheEntry<V>(
		V value,
		long ttl
	) {}
}
