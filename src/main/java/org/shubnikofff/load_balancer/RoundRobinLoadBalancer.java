package org.shubnikofff.load_balancer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class RoundRobinLoadBalancer implements LoadBalancer {

	private final List<String> serverList = new CopyOnWriteArrayList<>();

	private final AtomicInteger currentIndex = new AtomicInteger(0);


	@Override
	public void add(String server) {
		serverList.add(server);
	}

	@Override
	public String retrieve() {
		final var index = currentIndex.getAndUpdate(i -> (i + 1) % serverList.size());
		return serverList.get(index);
	}
}
