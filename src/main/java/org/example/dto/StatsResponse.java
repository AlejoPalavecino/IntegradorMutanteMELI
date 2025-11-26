package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatsResponse {

	private long count_mutant_dna;

	private long count_human_dna;

	private double ratio;
}
