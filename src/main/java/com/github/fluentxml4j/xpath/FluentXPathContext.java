package com.github.fluentxml4j.xpath;

import javax.xml.namespace.NamespaceContext;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

class FluentXPathContext
{
	private XPathFactory xPathFactory;

	FluentXPathContext()
	{
		xPathFactory = XPathFactory.newInstance();
	}

	XPathExpression compile(String xPathQuery, NamespaceContext namespaceContext)
	{
		try
		{
			XPath xPath = xPathFactory.newXPath();
			xPath.setNamespaceContext(namespaceContext);
			return xPath.compile(xPathQuery);
		}
		catch (XPathExpressionException ex)
		{
			throw new RuntimeException(ex);
		}
	}
}