package com.github.fluentxml4j.xpath;

import org.w3c.dom.NodeList;

import java.util.List;
import java.util.stream.Stream;

public interface SelectMultipleFromNode<NodeType>
{
	Iterable<NodeType> iterate();

	Stream<NodeType> asStream();

	List<NodeType> asList();

	NodeList asNodeList();
}