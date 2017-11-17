package com.github.fluentxml4j.internal.xpath;

import com.github.fluentxml4j.FluentXmlProcessingException;
import com.github.fluentxml4j.namespace.ImmutableNamespaceContext;
import com.github.fluentxml4j.xpath.QueryFromNode;
import com.github.fluentxml4j.xpath.SelectMultipleFromNode;
import com.github.fluentxml4j.xpath.XPathConfigurer;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.namespace.NamespaceContext;
import javax.xml.xpath.XPathExpression;
import java.util.Optional;
import java.util.function.Function;

class FromNodeImpl implements QueryFromNode
{
	private FluentXPathContext context;
	private Node baseNode;

	private NamespaceContext namespaceContext;

	FromNodeImpl(Node baseNode, FluentXPathContext context)
	{
		this.baseNode = baseNode;
		this.context = context;
		this.namespaceContext = new ImmutableNamespaceContext();
	}

	@Override
	public QueryFromNode withNamespaceContext(NamespaceContext namespaceContext)
	{
		this.namespaceContext = namespaceContext;
		return this;
	}

	@Override
	public QueryFromNode withNamespaceMappings(String... namespaceMappings)
	{
		checkImmutableNamespaceContext();

		this.namespaceContext = ((ImmutableNamespaceContext) this.namespaceContext).withMappings(namespaceMappings);
		return this;
	}

	private void checkImmutableNamespaceContext()
	{
		if (!(this.namespaceContext instanceof ImmutableNamespaceContext))
		{
			throw new IllegalStateException("Customized namespace cannot be customized.");
		}
	}

	@Override
	public QueryFromNode withNamespaceMapping(String prefix, String namespaceURI)
	{
		return withNamespaceMappings(prefix, namespaceURI);
	}

	@Override
	public QueryFromNode withXPath(XPathConfigurer xPathConfigurer)
	{
		this.context.setxPathConfigurer(xPathConfigurer);
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
		return selectUniqueNode(xPathQuery, ToStringConverter::toString);
	}

	@Override
	public Optional<Element> selectElement(String xPathQuery)
	{
		return selectUniqueNode(xPathQuery, ToElementConverter::toElement);
	}

	@Override
	public Optional<Node> selectNode(String xPathQuery)
	{
		return selectUniqueNode(xPathQuery, Function.identity());
	}

	private <T> Optional<T> selectUniqueNode(String xPathQuery, Function<Node, T> converter)
	{
		NodeList nodeList = selectNodes(xPathQuery).asNodeList();
		int length = nodeList.getLength();
		if (length == 0)
		{
			return Optional.empty();
		}
		else if (length > 1)
		{
			throw new FluentXmlProcessingException("Non unique result.");
		}
		else
		{
			return Optional.ofNullable(converter.apply(nodeList.item(0)));
		}
	}
}
