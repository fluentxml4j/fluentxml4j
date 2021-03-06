package com.github.fluentxml4j.internal.query;

import org.w3c.dom.Attr;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

interface ToStringConverter
{
	static String toString(Node node)
	{
		if (node == null)
		{
			return null;
		}
		else if (node instanceof Text)
		{
			return ((Text) node).getData();
		}
		else if (node instanceof Attr)
		{
			return ((Attr) node).getValue();
		}

		return node.toString();
	}
}
