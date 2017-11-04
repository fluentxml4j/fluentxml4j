package com.github.fluentxml4j.validator;

import org.w3c.dom.Document;

public interface ValidateNode
{
	ValidateWithSchemaNode withSchema(Document doc);
}
