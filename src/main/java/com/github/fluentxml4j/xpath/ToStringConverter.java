package com.github.fluentxml4j.xpath;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

interface ToStringConverter
{
	static String toString(Node node)
	{
		if (node instanceof Text)
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
