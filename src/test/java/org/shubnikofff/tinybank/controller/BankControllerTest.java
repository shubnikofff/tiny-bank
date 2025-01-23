package org.shubnikofff.tinybank.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.shubnikofff.tinybank.dto.TransactionRequest;
import org.shubnikofff.tinybank.service.TinyBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(BankController.class)
class BankControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private TinyBankService tinyBankService;

	private static final ObjectMapper objectMapper = new ObjectMapper();

	@Test
	void should_return_success_status_code_for_deposit_test() throws Exception {
		final var requestBody = objectMapper.writeValueAsBytes(new TransactionRequest(BigDecimal.TEN));
		mockMvc.perform(post("/api/v1/deposit").contentType(MediaType.APPLICATION_JSON).content(requestBody))
			.andExpect(status().isOk());
	}

	@Test
	void should_return_success_status_code_for_withdraw_test() throws Exception {
		final var requestBody = objectMapper.writeValueAsBytes(new TransactionRequest(BigDecimal.TEN));
		mockMvc.perform(post("/api/v1/withdraw").contentType(MediaType.APPLICATION_JSON).content(requestBody))
			.andExpect(status().isOk());
	}

	@ParameterizedTest
	@MethodSource("badTransactionRequestBody")
	void should_return_bad_request_status_in_case_of_incorrect_request_body_test(byte[] requestBody) throws Exception {
		mockMvc.perform(post("/api/v1/deposit").contentType(MediaType.APPLICATION_JSON).content(requestBody))
			.andExpect(status().isBadRequest());

		mockMvc.perform(post("/api/v1/withdraw").contentType(MediaType.APPLICATION_JSON).content(requestBody))
			.andExpect(status().isBadRequest());
	}

	@Test
	void should_return_success_status_code_on_get_balance_endpoint_test() throws Exception {
		when(tinyBankService.getBalance()).thenReturn(BigDecimal.TEN);
		mockMvc.perform(get("/api/v1/balance"))
			.andExpect(status().isOk())
			.andExpect(content().bytes(BigDecimal.TEN.toPlainString().getBytes()));
	}

	@Test
	void should_return_success_status_code_on_get_transaction_history_endpoint_test() throws Exception {
		when(tinyBankService.getTransactionHistory()).thenReturn(List.of());
		mockMvc.perform(get("/api/v1/transaction-history"))
			.andExpect(status().isOk())
			.andExpect(content().json("[]"));
	}

	private static List<byte[]> badTransactionRequestBody() {
		return Stream.of(
				new TransactionRequest(null), // empty amount
				new TransactionRequest(BigDecimal.ZERO), // amount is less than minimum
				new TransactionRequest(BigDecimal.valueOf(1_000_000_000.01)) // amount is greater than maximum
			)
			.map(value -> {
				try {
					return objectMapper.writeValueAsBytes(value);
				} catch (JsonProcessingException e) {
					throw new RuntimeException(e);
				}
			})
			.toList();
	}
}
