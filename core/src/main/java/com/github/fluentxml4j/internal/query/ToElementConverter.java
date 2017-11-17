package com.github.fluentxml4j.internal.query;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

interface ToElementConverter
{
	static Element toElement(Node node)
	{
		return (Element) node;
	}
}
