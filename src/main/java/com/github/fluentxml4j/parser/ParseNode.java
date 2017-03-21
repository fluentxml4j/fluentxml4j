package com.github.fluentxml4j.parser;

import org.w3c.dom.Document;

public interface ParseNode
{
	ParseNode withNamespaces(boolean namespacesEnabled);

	Document asDocument();
}
