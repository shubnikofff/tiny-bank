package org.shubnikofff.load_balancer;

import java.util.Optional;

public interface LoadBalancer {

	void add(String server);

	String retrieve();

}
