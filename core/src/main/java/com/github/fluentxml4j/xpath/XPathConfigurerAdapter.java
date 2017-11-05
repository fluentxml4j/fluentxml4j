package com.github.fluentxml4j.xpath;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

public class XPathConfigurerAdapter implements XPathConfigurer
{
	public XPath getXPath(ImmutableNamespaceContext namespaceContext)
	{
		XPathFactory xPathFactory = buildXPathFactory();
		configure(xPathFactory);
		XPath xPath = buildXPath(xPathFactory);
		configure(xPath, namespaceContext);
		return xPath;
	}

	private XPath buildXPath(XPathFactory xPathFactory)
	{
		return xPathFactory.newXPath();
	}

	protected void configure(XPath xPath, ImmutableNamespaceContext namespaceContext)
	{
		xPath.setNamespaceContext(namespaceContext);
	}

	protected void configure(XPathFactory xPathFactory)
	{
	}

	protected XPathFactory buildXPathFactory()
	{
		return XPathFactory.newInstance();
	}
}
