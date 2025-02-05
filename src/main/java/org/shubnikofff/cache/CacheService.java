package org.shubnikofff.cache;

import java.util.Optional;

public interface CacheService<K, V> {

	void put(K key, V value, long ttl);

	Optional<V> get(K key);

}
