package com.github.fluentxml4j.transformer;

import org.w3c.dom.Document;

import java.io.InputStream;
import java.net.URL;

public interface TransformNode
{
	TransformNode withStylesheet(URL url);

	TransformNode withStylesheet(InputStream in);

	TransformNode withStylesheet(Document doc);

	Document toDocument();

	String toString();
}
