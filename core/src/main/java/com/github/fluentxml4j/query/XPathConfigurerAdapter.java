package com.github.fluentxml4j.query;

import javax.xml.namespace.NamespaceContext;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

public class XPathConfigurerAdapter implements XPathConfigurer
{
	public XPath getXPath(NamespaceContext namespaceContext)
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

	protected void configure(XPath xPath, NamespaceContext namespaceContext)
	{
		xPath.setNamespaceContext(namespaceContext);
	}

	protected void configure(XPathFactory xPathFactory)
	{
		// override for customization
	}

	protected XPathFactory buildXPathFactory()
	{
		return XPathFactory.newInstance();
	}
}
