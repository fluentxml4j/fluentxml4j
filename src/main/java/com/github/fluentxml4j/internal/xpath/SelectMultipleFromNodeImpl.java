package com.github.fluentxml4j.internal.xpath;

import com.github.fluentxml4j.xpath.SelectMultipleFromNode;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathExpression;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

class SelectMultipleFromNodeImpl<ResultType> implements SelectMultipleFromNode<ResultType>
{
	private Node baseNode;
	private XPathExpression xPathExpression;
	private Function<Node, ResultType> nodeConverter;

	SelectMultipleFromNodeImpl(Node baseNode, XPathExpression xPathExpression, Function<Node, ResultType> nodeConverter)
	{
		this.baseNode = baseNode;
		this.xPathExpression = xPathExpression;
		this.nodeConverter = nodeConverter;
	}

	@Override
	public Iterator<ResultType> iterator()
	{
		return iterate().iterator();
	}

	@Deprecated
	@Override
	public Iterable<ResultType> iterate()
	{
		NodeList nodeList = asNodeList();
		return () -> new NodeListIterator<>(nodeList, this.nodeConverter);
	}

	@Override
	public Stream<ResultType> asStream()
	{
		return StreamSupport.stream(iterate().spliterator(), true);
	}

	@Override
	public NodeList asNodeList()
	{
		try
		{
			return (NodeList) this.xPathExpression.evaluate(this.baseNode, XPathConstants.NODESET);
		}
		catch (XPathException ex)
		{
			throw new RuntimeException(ex);
		}
	}

	@Override
	public List<ResultType> asList()
	{
		return asStream().collect(Collectors.toList());
	}


}
