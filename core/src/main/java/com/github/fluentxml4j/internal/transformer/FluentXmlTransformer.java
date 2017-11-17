package com.github.fluentxml4j.internal.transformer;

import com.github.fluentxml4j.transformer.TransformNode;
import org.w3c.dom.Document;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stax.StAXSource;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.InputStream;

public class FluentXmlTransformer
{
	public TransformNode transform(Document doc)
	{
		return new TransformNodeImpl(new DOMSource(doc));
	}

	public TransformNode transform(XMLEventReader in)
	{
		return new TransformNodeImpl(in);
	}

	public TransformNode transform(XMLStreamReader in)
	{
		return new TransformNodeImpl(in);
	}

	public TransformNode transform(InputStream in)
	{
		return new TransformNodeImpl(new StreamSource(in));
	}

	public TransformNode transform(File in)
	{
		return new TransformNodeImpl(new StreamSource(in));
	}
}
