package com.github.fluentxml4j.internal.query;

import com.github.fluentxml4j.query.QueryFromNode;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class FluentQuery
{
	public QueryFromNode from(Node node)
	{
		return new FromNodeImpl(node, new FluentXPathContext());
	}
}
