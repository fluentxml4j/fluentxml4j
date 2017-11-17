package com.github.fluentxml4j.internal.query;

import org.w3c.dom.Node;

interface ToIntegerConverter
{
	static Integer toInteger(Node node)
	{
		String s = ToStringConverter.toString(node);
		if (s == null)
		{
			return null;
		}

		return Integer.valueOf(s);
	}
}
