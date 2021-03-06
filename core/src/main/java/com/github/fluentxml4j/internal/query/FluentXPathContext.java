package com.github.fluentxml4j.internal.query;

import com.github.fluentxml4j.FluentXmlProcessingException;
import com.github.fluentxml4j.query.XPathConfigurer;
import com.github.fluentxml4j.query.XPathConfigurerAdapter;

import javax.xml.namespace.NamespaceContext;
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

	XPathExpression compile(String xPathQuery, NamespaceContext namespaceContext)
	{
		try
		{
			XPath xPath = this.xPathConfigurer.getXPath(namespaceContext);
			return xPath.compile(xPathQuery);
		}
		catch (XPathExpressionException ex)
		{
			throw new FluentXmlProcessingException(ex);
		}
	}
}