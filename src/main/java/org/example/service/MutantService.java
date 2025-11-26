package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.DnaRecord;
import org.example.repository.DnaRecordRepository;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class MutantService {

	private final MutantDetector mutantDetector;
	private final DnaRecordRepository dnaRecordRepository;

	public boolean analyzeDna(String[] dna) {
		String hash = calculateHash(dna);

		return dnaRecordRepository.findByDnaHash(hash)
			.map(DnaRecord::isMutant)
			.orElseGet(() -> {
				boolean isMutant = mutantDetector.isMutant(dna);
				DnaRecord record = DnaRecord.builder()
					.dnaHash(hash)
					.isMutant(isMutant)
					.build();
				dnaRecordRepository.save(record);
				return isMutant;
			});
	}

	private String calculateHash(String[] dna) {
		if (dna == null) {
			throw new IllegalArgumentException("El ADN no puede ser nulo");
		}
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			for (String row : dna) {
				if (row == null) {
					throw new IllegalArgumentException("Las filas de ADN no pueden ser nulas");
				}
				digest.update(row.getBytes(StandardCharsets.UTF_8));
				digest.update((byte) '|'); // Separador para evitar colisiones por concatenación simple
			}
			byte[] hashBytes = digest.digest();
			StringBuilder hex = new StringBuilder(hashBytes.length * 2);
			for (byte b : hashBytes) {
				hex.append(String.format("%02x", b));
			}
			return hex.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("SHA-256 no está disponible en la JVM", e);
		}
	}
}
