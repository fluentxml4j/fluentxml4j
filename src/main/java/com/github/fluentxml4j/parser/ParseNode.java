package com.github.fluentxml4j.parser;

import org.w3c.dom.Document;

public interface ParseNode
{
	ParseWithDocumentBuilderNode withDocumentBuilder(DocumentBuilderConfigurer documentBuilderConfigurer);

	Document document();

	/**
	 * @deprecated Use {@link #document()} instead.
	 */
	@Deprecated
	Document asDocument();
}
