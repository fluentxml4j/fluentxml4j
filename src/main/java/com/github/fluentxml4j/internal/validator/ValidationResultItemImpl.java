package com.github.fluentxml4j.internal.validator;

import com.github.fluentxml4j.validator.Severity;
import com.github.fluentxml4j.validator.ValidationResultItem;

public class ValidationResultItemImpl implements ValidationResultItem
{
	private Severity severity;
	private String description;

	ValidationResultItemImpl(Severity severity, String description)
	{
		this.severity = severity;
		this.description = description;
	}

	@Override
	public Severity severity()
	{
		return this.severity;
	}

	@Override
	public String description()
	{
		return this.description;
	}
}
