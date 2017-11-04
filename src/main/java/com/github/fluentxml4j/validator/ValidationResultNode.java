package com.github.fluentxml4j.validator;

import java.util.List;

public interface ValidationResultNode
{
	boolean isValid();

	List<ValidationResultItem> asList();
}
