package com.github.fluentxml4j.internal.xpath;

import com.github.fluentxml4j.xpath.FromNode;
import org.w3c.dom.Document;

public class FluentXPath
{
	public FromNode from(Document doc)
	{
		return new FromNodeImpl(doc, new FluentXPathContext());
	}
}
