package com.github.fluentxml4j.xpath;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.Optional;

public interface FromNode
{
	FromNode withNamespace(String prefix, String namespaceURI);

	FromNode withXPath(XPathConfigurer xPathConfigurer);

	Optional<String> selectString(String xPathQuery);

	Optional<Element> selectElement(String xPathQuery);

	Optional<Node> selectNode(String xPathQuery);

	SelectMultipleFromNode<Element> selectElements(String xPathQuery);

	SelectMultipleFromNode<String> selectStrings(String xPathQuery);

	SelectMultipleFromNode<Node> selectNodes(String xPathQuery);
}
