package com.github.fluentxml4j.internal.transform;

import com.github.fluentxml4j.internal.util.JaxbUtils;
import com.github.fluentxml4j.transform.TransformNode;
import org.w3c.dom.Document;

import javax.xml.bind.JAXBContext;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.InputStream;
import java.net.URL;

public class FluentXmlTransformer
{
	public TransformNode transform(Document doc)
	{
		return new TransformNodeImpl(new DOMSource(doc));
	}

	public TransformNode transform(org.jdom2.Document doc)
	{
		return new TransformNodeImpl(new org.jdom2.transform.JDOMSource(doc));
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

	public TransformNode transform(URL url)
	{
		return new TransformNodeImpl(new StreamSource(url.toExternalForm()));
	}

	public TransformNode transform(JAXBContext jaxbContext, Object object)
	{
		return new TransformNodeImpl(JaxbUtils.newJAXBSource(jaxbContext, object));
	}
}
