package com.github.fluentxml4j.xpath;

import javax.xml.xpath.XPath;

public interface XPathConfigurer
{
	XPath getXPath(ImmutableNamespaceContext namespaceContext);
}
