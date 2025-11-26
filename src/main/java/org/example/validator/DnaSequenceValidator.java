package org.example.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class DnaSequenceValidator implements ConstraintValidator<ValidDnaSequence, String[]> {

	private static final Pattern VALID_PATTERN = Pattern.compile("^[ATCG]+$");

	@Override
	public boolean isValid(String[] value, ConstraintValidatorContext context) {
		if (value == null || value.length == 0) {
			return false;
		}

		int size = value.length;
		for (String row : value) {
			if (row == null || row.length() != size) {
				return false; // Debe ser matriz cuadrada y sin filas nulas
			}
			if (!VALID_PATTERN.matcher(row).matches()) {
				return false; // Caracteres inválidos
			}
		}
		return true;
	}
}
