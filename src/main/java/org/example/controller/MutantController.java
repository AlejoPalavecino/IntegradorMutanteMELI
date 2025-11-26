package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.DnaRequest;
import org.example.dto.StatsResponse;
import org.example.service.MutantService;
import org.example.service.StatsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@Tag(name = "Mutant API", description = "Detección de ADN mutante y estadísticas")
@RequiredArgsConstructor
public class MutantController {

	private final MutantService mutantService;
	private final StatsService statsService;

	@PostMapping("mutant")
	@Operation(summary = "Detecta si una secuencia de ADN corresponde a un mutante")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "El ADN es mutante"),
		@ApiResponse(responseCode = "400", description = "Solicitud inválida"),
		@ApiResponse(responseCode = "403", description = "El ADN no es mutante")
	})
	public ResponseEntity<Void> validateMutant(@Valid @RequestBody DnaRequest request) {
		boolean isMutant = mutantService.analyzeDna(request.getDna());
		return isMutant ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}

	@GetMapping("stats")
	@Operation(summary = "Obtiene estadísticas de verificaciones de ADN")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Estadísticas calculadas"),
		@ApiResponse(responseCode = "400", description = "Solicitud inválida")
	})
	public ResponseEntity<StatsResponse> getStats() {
		return ResponseEntity.ok(statsService.getStats());
	}
}
