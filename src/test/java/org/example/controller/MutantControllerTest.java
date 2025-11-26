package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.StatsResponse;
import org.example.service.MutantService;
import org.example.service.StatsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MutantController.class)
class MutantControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private MutantService mutantService;

	@MockBean
	private StatsService statsService;

	@Test
	@DisplayName("POST /mutant retorna 200 cuando el ADN es mutante")
	void testMutantReturns200() throws Exception {
		when(mutantService.analyzeDna(any())).thenReturn(true);
		String body = objectMapper.writeValueAsString(
			new TestRequest(new String[]{"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"})
		);

		mockMvc.perform(post("/mutant")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body))
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("POST /mutant retorna 403 cuando el ADN es humano")
	void testHumanReturns403() throws Exception {
		when(mutantService.analyzeDna(any())).thenReturn(false);
		String body = objectMapper.writeValueAsString(
			new TestRequest(new String[]{"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"})
		);

		mockMvc.perform(post("/mutant")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body))
			.andExpect(status().isForbidden());
	}

	@Test
	@DisplayName("POST /mutant retorna 400 con ADN inválido")
		void testInvalidDnaReturns400() throws Exception {
		String body = objectMapper.writeValueAsString(
			new TestRequest(new String[]{"ATGZ", "CAGT", "TTAT", "AGAA"})
		);

		mockMvc.perform(post("/mutant")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body))
			.andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("GET /stats retorna 200 con payload esperado")
	void testStatsReturns200() throws Exception {
		StatsResponse response = StatsResponse.builder()
			.count_mutant_dna(40)
			.count_human_dna(100)
			.ratio(0.4)
			.build();
		when(statsService.getStats()).thenReturn(response);

		mockMvc.perform(get("/stats"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.count_mutant_dna").value(40))
			.andExpect(jsonPath("$.count_human_dna").value(100))
			.andExpect(jsonPath("$.ratio").value(0.4));
	}

	// DTO interno de apoyo para construir el JSON sin depender de DnaRequest directamente
	private record TestRequest(String[] dna) {}
}
