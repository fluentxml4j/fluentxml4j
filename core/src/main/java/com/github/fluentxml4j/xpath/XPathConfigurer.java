package com.github.fluentxml4j.xpath;

import javax.xml.namespace.NamespaceContext;
import javax.xml.xpath.XPath;

public interface XPathConfigurer
{
	XPath getXPath(NamespaceContext namespaceContext);
}
