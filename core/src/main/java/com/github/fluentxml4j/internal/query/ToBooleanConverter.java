package com.github.fluentxml4j.internal.query;

import org.w3c.dom.Node;

interface ToBooleanConverter
{
	static Boolean toBoolean(Node node)
	{
		String s = ToStringConverter.toString(node);
		if (s == null)
		{
			return null;
		}

		return Boolean.valueOf(s);
	}
}
