package com.github.fluentxml4j.query;

import org.w3c.dom.NodeList;

import java.util.List;
import java.util.stream.Stream;

public interface SelectMultipleFromNode<NodeType> extends Iterable<NodeType>
{
	/**
	 * @deprecated SelectMultipleFromNode already is an instance of {@link Iterable}.
	 * @return Iterable instance of result.
	 */
	@Deprecated
	Iterable<NodeType> iterate();

	Stream<NodeType> asStream();

	List<NodeType> asList();

	NodeList asNodeList();
}