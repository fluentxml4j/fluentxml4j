package com.github.fluentxml4j.transformer;

import com.github.fluentxml4j.serializer.SerializeWithTransformerNode;
import com.github.fluentxml4j.serializer.SerializerConfigurer;
import com.github.fluentxml4j.serializer.SerializerConfigurerAdapter;
import org.w3c.dom.Document;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.net.URL;

class TransformNodeImpl implements TransformNode
{
	private TransformationChain transformationChain;

	TransformNodeImpl(Source source)
	{
		this.transformationChain = new TransformationChain(source);
	}

	@Override
	public TransformNode withStylesheet(URL url)
	{
		try
		{
			return withStylesheet(new StreamSource(url.openStream(), url.toExternalForm()));
		}
		catch (IOException ex)
		{
			throw new RuntimeException(ex);
		}
	}

	@Override
	public TransformNode withStylesheet(InputStream in)
	{
		return withStylesheet(new StreamSource(in));
	}

	@Override
	public TransformNode withStylesheet(Document doc)
	{
		return withStylesheet(new DOMSource(doc));
	}

	private TransformNode withStylesheet(Source xslt)
	{
		try
		{
			SAXTransformerFactory saxTransformerFactory = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
			TransformerHandler xsltTransformer = saxTransformerFactory.newTransformerHandler(xslt);
			this.transformationChain.addTransformer(xsltTransformer);

			return this;
		}
		catch (TransformerException ex)
		{
			throw new RuntimeException(ex);
		}
	}

	@Override
	public SerializeWithTransformerNode withSerializer(SerializerConfigurer serializerConfigurer)
	{
		this.transformationChain.addTransformer(serializerConfigurer.getSerializer());

		return new TransformWithSerializerNodeImpl(transformationChain);
	}

	@Override
	public void to(OutputStream out)
	{
		withSerializer(new SerializerConfigurerAdapter()).to(out);
	}

	@Override
	public void to(Writer out)
	{
		withSerializer(new SerializerConfigurerAdapter()).to(out);
	}

	@Override
	public String toString()
	{
		return withSerializer(new SerializerConfigurerAdapter()).toString();
	}

	@Override
	public Document toDocument()
	{
		return this.transformationChain.transformToDocument();
	}
}
