package com.github.fluentxml4j.validate;

import org.w3c.dom.Document;

public interface ValidateNode
{
	ValidateNode againstSchema(Document schemaDoc);

	ValidationResult getResult();
}
