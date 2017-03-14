package com.github.fluentxml4j.xpath;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;

class NodeListIterator<ResultType> implements Iterator<ResultType>
{
	private NodeList nodeList;
	private Function<Node, ResultType> nodeConverter;
	private int pos = 0;

	NodeListIterator(NodeList nodeList, Function<Node, ResultType> nodeConverter)
	{
		this.nodeList = nodeList;
		this.nodeConverter = nodeConverter;
	}

	@Override
	public boolean hasNext()
	{
		return this.nodeList != null && this.pos < this.nodeList.getLength();
	}

	@Override
	public ResultType next()
	{
		if (this.nodeList == null || this.pos >= this.nodeList.getLength())
		{
			throw new NoSuchElementException();
		}

		Node nextNode = nodeList.item(this.pos++);
		ResultType next = this.nodeConverter.apply(nextNode);
		return next;
	}
}
