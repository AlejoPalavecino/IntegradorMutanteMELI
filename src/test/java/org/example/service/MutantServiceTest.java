package org.example.service;

import org.example.entity.DnaRecord;
import org.example.repository.DnaRecordRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MutantServiceTest {

	@Mock
	private MutantDetector mutantDetector;

	@Mock
	private DnaRecordRepository dnaRecordRepository;

	@InjectMocks
	private MutantService mutantService;

	@Test
	@DisplayName("Guarda ADN mutante cuando no existe en caché")
	void testAnalyzeDnaMutant() {
		String[] dna = {"AAAA", "CAGT", "TTAT", "AGAA"};
		when(dnaRecordRepository.findByDnaHash(anyString())).thenReturn(Optional.empty());
		when(mutantDetector.isMutant(dna)).thenReturn(true);

		boolean result = mutantService.analyzeDna(dna);

		assertTrue(result);
		ArgumentCaptor<DnaRecord> captor = ArgumentCaptor.forClass(DnaRecord.class);
		verify(dnaRecordRepository).save(captor.capture());
		assertTrue(captor.getValue().isMutant());
		verify(mutantDetector, times(1)).isMutant(dna);
	}

	@Test
	@DisplayName("Guarda ADN humano cuando no existe en caché")
	void testAnalyzeDnaHuman() {
		String[] dna = {"ATGC", "CAGT", "TTAT", "AGAA"};
		when(dnaRecordRepository.findByDnaHash(anyString())).thenReturn(Optional.empty());
		when(mutantDetector.isMutant(dna)).thenReturn(false);

		boolean result = mutantService.analyzeDna(dna);

		assertFalse(result);
		ArgumentCaptor<DnaRecord> captor = ArgumentCaptor.forClass(DnaRecord.class);
		verify(dnaRecordRepository).save(captor.capture());
		assertFalse(captor.getValue().isMutant());
		verify(mutantDetector, times(1)).isMutant(dna);
	}

	@Test
	@DisplayName("Cache hit: retorna valor almacenado sin procesar")
	void testCacheHit() {
		String[] dna = {"ATGC", "CAGT", "TTAT", "AGAA"};
		DnaRecord record = DnaRecord.builder()
			.id(1L)
			.dnaHash("hash")
			.isMutant(true)
			.build();
		when(dnaRecordRepository.findByDnaHash(anyString())).thenReturn(Optional.of(record));

		boolean result = mutantService.analyzeDna(dna);

		assertTrue(result);
		verify(mutantDetector, never()).isMutant(any());
		verify(dnaRecordRepository, never()).save(any());
	}
}
