package com.github.fluentxml4j.validate;

import java.util.List;

public class ValidationResult
{
	private List<ValidationError> errors;

	public ValidationResult(List<ValidationError> errors)
	{
		this.errors = errors;
	}

	public boolean hasErrors() {
		return !this.errors.isEmpty();
	}

	public List<ValidationError> getErrors() {
		return this.errors;
	}
}
