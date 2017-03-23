package com.github.fluentxml4j.xpath;

import org.w3c.dom.NodeList;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;

public interface SelectMultipleFromNode<NodeType> extends Iterable<NodeType>
{
	/**
	 * @deprecated SelectMultipleFromNode already is an instance of {@link Iterable<NodeType>}.
	 */
	@Deprecated
	Iterable<NodeType> iterate();

	Stream<NodeType> asStream();

	List<NodeType> asList();

	NodeList asNodeList();
}