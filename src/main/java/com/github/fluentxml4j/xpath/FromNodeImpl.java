package com.github.fluentxml4j.xpath;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathExpression;
import java.util.Optional;
import java.util.function.Function;

class FromNodeImpl implements FromNode
{
	private FluentXPathContext context;
	private Node baseNode;

	private ImmutableNamespaceContext namespaceContext;

	FromNodeImpl(Node baseNode, FluentXPathContext context)
	{
		this.baseNode = baseNode;
		this.context = context;
		this.namespaceContext = new ImmutableNamespaceContext();
	}

	@Override
	public FromNode withNamespace(String prefix, String namespaceURI)
	{
		this.namespaceContext = this.namespaceContext.addMapping(prefix, namespaceURI);
		return this;
	}

	@Override
	public SelectMultipleFromNode<String> selectStrings(String xPathQuery)
	{
		XPathExpression xPathExpression = this.context.compile(xPathQuery, this.namespaceContext);
		return new SelectMultipleFromNodeImpl<>(baseNode, xPathExpression, ToStringConverter::toString);
	}

	@Override
	public SelectMultipleFromNode<Node> selectNodes(String xPathQuery)
	{
		XPathExpression xPathExpression = this.context.compile(xPathQuery, this.namespaceContext);
		return new SelectMultipleFromNodeImpl<>(baseNode, xPathExpression, Function.identity());
	}

	@Override
	public SelectMultipleFromNode<Element> selectElements(String xPathQuery)
	{
		XPathExpression xPathExpression = this.context.compile(xPathQuery, this.namespaceContext);
		return new SelectMultipleFromNodeImpl<>(baseNode, xPathExpression, ToElementConverter::toElement);
	}

	@Override
	public Optional<String> selectString(String xPathQuery)
	{
		return selectNode(xPathQuery, ToStringConverter::toString);
	}

	@Override
	public Optional<Element> selectElement(String xPathQuery)
	{
		return selectNode(xPathQuery, ToElementConverter::toElement);
	}

	@Override
	public Optional<Node> selectNode(String xPathQuery)
	{
		return selectNode(xPathQuery, Function.identity());
	}

	private <ResultType> Optional<ResultType> selectNode(String xPathQuery, Function<Node, ResultType> converter)
	{
		NodeList nodeList = selectNodes(xPathQuery).asNodeList();
		if (nodeList.getLength() == 0)
		{
			return Optional.empty();
		}
		else
		{
			return Optional.ofNullable(converter.apply(nodeList.item(0)));
		}
	}
}
