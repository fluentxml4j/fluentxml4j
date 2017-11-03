package com.github.fluentxml4j.transformer;

import org.w3c.dom.Document;

import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;

public class FluentXmlTransformer
{
	public TransformNode transform(Document doc)
	{
		return new TransformNodeImpl(new DOMSource(doc));
	}

	public TransformNode transform(InputStream in)
	{
		return new TransformNodeImpl(new StreamSource(in));
	}
}
