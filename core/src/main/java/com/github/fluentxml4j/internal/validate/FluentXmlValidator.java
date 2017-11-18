package com.github.fluentxml4j.internal.validate;

import com.github.fluentxml4j.validate.ValidateNode;
import org.w3c.dom.Document;

public class FluentXmlValidator
{
	public ValidateNode validate(Document doc) {
		return new ValidateNodeImpl(doc);
	}
}
