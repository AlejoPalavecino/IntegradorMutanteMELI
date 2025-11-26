package org.example.service;

import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class MutantDetector {

	private static final Set<Character> VALID_BASES = Set.of('A', 'T', 'C', 'G');

	public boolean isMutant(String[] dna) {
		if (dna == null || dna.length == 0) {
			return false;
		}

		int size = dna.length;
		char[][] matrix = new char[size][size]; // Acceso O(1) a índices; evita el overhead de usar charAt repetidamente

		for (int i = 0; i < size; i++) {
			String row = dna[i];
			if (row == null || row.length() != size) {
				throw new IllegalArgumentException("DNA matrix must be square");
			}
			for (int j = 0; j < size; j++) {
				char base = row.charAt(j);
				if (!VALID_BASES.contains(base)) {
					throw new IllegalArgumentException("Invalid DNA character: " + base);
				}
				matrix[i][j] = base;
			}
		}

		int sequenceCount = 0;
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				char base = matrix[row][col];

				// Horizontal →
				if (col + 3 < size
					&& base == matrix[row][col + 1]
					&& base == matrix[row][col + 2]
					&& base == matrix[row][col + 3]) {
					sequenceCount++;
					if (sequenceCount > 1) {
						return true; // Terminación temprana cuando supera una secuencia
					}
				}

				// Vertical ↓
				if (row + 3 < size
					&& base == matrix[row + 1][col]
					&& base == matrix[row + 2][col]
					&& base == matrix[row + 3][col]) {
					sequenceCount++;
					if (sequenceCount > 1) {
						return true;
					}
				}

				// Diagonal ↘
				if (row + 3 < size && col + 3 < size
					&& base == matrix[row + 1][col + 1]
					&& base == matrix[row + 2][col + 2]
					&& base == matrix[row + 3][col + 3]) {
					sequenceCount++;
					if (sequenceCount > 1) {
						return true;
					}
				}

				// Diagonal ↙
				if (row + 3 < size && col - 3 >= 0
					&& base == matrix[row + 1][col - 1]
					&& base == matrix[row + 2][col - 2]
					&& base == matrix[row + 3][col - 3]) {
					sequenceCount++;
					if (sequenceCount > 1) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
