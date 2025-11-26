package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.StatsResponse;
import org.example.repository.DnaRecordRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatsService {

	private final DnaRecordRepository dnaRecordRepository;

	public StatsResponse getStats() {
		long mutants = dnaRecordRepository.countByIsMutant(true);
		long humans = dnaRecordRepository.countByIsMutant(false);
		double ratio = humans == 0 ? 0.0 : (double) mutants / humans;
		return StatsResponse.builder()
			.count_mutant_dna(mutants)
			.count_human_dna(humans)
			.ratio(ratio)
			.build();
	}
}
