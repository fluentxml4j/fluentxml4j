package com.github.fluentxml4j.internal.xpath;

import com.github.fluentxml4j.xpath.ImmutableNamespaceContext;
import com.github.fluentxml4j.xpath.XPathConfigurer;
import com.github.fluentxml4j.xpath.XPathConfigurerAdapter;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

class FluentXPathContext
{
	private XPathConfigurer xPathConfigurer = new XPathConfigurerAdapter();

	void setxPathConfigurer(XPathConfigurer xPathConfigurer)
	{
		this.xPathConfigurer = xPathConfigurer;
	}

	XPathExpression compile(String xPathQuery, ImmutableNamespaceContext namespaceContext)
	{
		try
		{
			XPath xPath = this.xPathConfigurer.getXPath(namespaceContext);
			return xPath.compile(xPathQuery);
		}
		catch (XPathExpressionException ex)
		{
			throw new RuntimeException(ex);
		}
	}
}