package com.github.fluentxml4j.internal.query;

import com.github.fluentxml4j.query.QueryFromNode;
import org.w3c.dom.Document;

public class FluentXPath
{
	public QueryFromNode from(Document doc)
	{
		return new FromNodeImpl(doc, new FluentXPathContext());
	}
}
