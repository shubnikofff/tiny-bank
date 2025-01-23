package org.shubnikofff.tinybank;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class TinyBankApplicationTests {

	@Test
	void should_load_context_test() {
		assertThat(Boolean.TRUE).isTrue();
	}

}
