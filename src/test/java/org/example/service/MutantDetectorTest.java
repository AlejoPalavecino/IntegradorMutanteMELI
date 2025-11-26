package org.example.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MutantDetectorTest {

	private final MutantDetector detector = new MutantDetector();

	@Test
	@DisplayName("Mutante: dos secuencias horizontales detectadas")
	void testMutantHorizontal() {
		String[] dna = {
			"AAAACC",
			"AAAAGG",
			"TTTTCC",
			"CAGTGC",
			"GATTAC",
			"CCCGTA"
		};
		assertTrue(detector.isMutant(dna));
	}

	@Test
	@DisplayName("Mutante: secuencia vertical y otra adicional")
	void testMutantVertical() {
		String[] dna = {
			"ATGCGA",
			"AAGCGC",
			"ATACGT",
			"AAAGGG",
			"CCCCTA",
			"TCACTG"
		};
		assertTrue(detector.isMutant(dna));
	}

	@Test
	@DisplayName("Mutante: secuencia en diagonal principal y otra adicional")
	void testMutantDiagonalPrincipal() {
		String[] dna = {
			"ATGCTA",
			"AATGCC",
			"TAATGT",
			"CGAAAG",
			"CCCCAA",
			"TCACTG"
		};
		assertTrue(detector.isMutant(dna));
	}

	@Test
	@DisplayName("Mutante: secuencia en diagonal inversa y otra adicional")
	void testMutantDiagonalInvertida() {
		String[] dna = {
			"ATGGGG",
			"CAGGGC",
			"TGAGGC",
			"GTTTGA",
			"CCCTAA",
			"TCACTA"
		};
		assertTrue(detector.isMutant(dna));
	}

	@Test
	@DisplayName("Mutante: múltiples secuencias en diferentes direcciones")
	void testMutantMultipleSequences() {
		String[] dna = {
			"AGGCGT",
			"CAGTGC",
			"TTATGT",
			"AGAAGG",
			"CCCCTA",
			"TCACTG"
		};
		assertTrue(detector.isMutant(dna));
	}

	@Test
	@DisplayName("Humano: sin secuencias de cuatro letras")
	void testHumanNoSequence() {
		String[] dna = {
			"ATCGTA",
			"CAGTGC",
			"TTATGT",
			"AGTAGG",
			"CGCCTA",
			"TCACTG"
		};
		assertFalse(detector.isMutant(dna));
	}

	@Test
	@DisplayName("Humano: exactamente una secuencia, no debe considerarse mutante")
	void testHumanOneSequence() {
		String[] dna = {
			"AAAA",
			"TCGA",
			"CGTT",
			"ATCG"
		};
		assertFalse(detector.isMutant(dna));
	}

	@Test
	@DisplayName("Validación: ADN nulo retorna false")
	void testNullDna() {
		assertFalse(detector.isMutant(null));
	}

	@Test
	@DisplayName("Validación: ADN vacío retorna false")
	void testEmptyDna() {
		assertFalse(detector.isMutant(new String[]{}));
	}

	@Test
	@DisplayName("Validación: matriz no cuadrada lanza excepción")
	void testNxM() {
		String[] dna = {
			"ATGC",
			"CAGT",
			"TTAT",
			"AGA"
		};
		assertThrows(IllegalArgumentException.class, () -> detector.isMutant(dna));
	}

	@Test
	@DisplayName("Validación: caracteres inválidos lanzan excepción")
	void testInvalidData() {
		String[] dna = {
			"ATGX",
			"CAGT",
			"TTAT",
			"AGAA"
		};
		assertThrows(IllegalArgumentException.class, () -> detector.isMutant(dna));
	}

	@Test
	@DisplayName("Validación: fila nula lanza excepción")
	void testNullRow() {
		String[] dna = {
			"ATGC",
			null,
			"TTAT",
			"AGAA"
		};
		assertThrows(IllegalArgumentException.class, () -> detector.isMutant(dna));
	}

	@Test
	@DisplayName("Borde: matriz mínima 4x4 con dos secuencias")
	void testSmallMatrix() {
		String[] dna = {
			"AAAA",
			"AAGG",
			"TTAA",
			"GGAA"
		};
		assertTrue(detector.isMutant(dna));
	}

	@Test
	@DisplayName("Borde: matriz grande 100x100 sin secuencias")
	void testLargeMatrix() {
		int size = 100;
		String[] dna = new String[size];
		char[] bases = {'A', 'T', 'C', 'G'};
		for (int i = 0; i < size; i++) {
			char[] row = new char[size];
			for (int j = 0; j < size; j++) {
				row[j] = bases[(i + j) % bases.length];
			}
			dna[i] = new String(row);
		}
		assertFalse(detector.isMutant(dna));
	}

	@Test
	@DisplayName("Borde: secuencias cerca de los límites de la matriz")
	void testSequenceAtMatrixBoundary() {
		String[] dna = {
			"AGCTA",
			"CAGCT",
			"TCAGC",
			"GTCAG",
			"TTTTG"
		};
		assertTrue(detector.isMutant(dna));
	}
}
