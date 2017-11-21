package com.github.fluentxml4j.internal.transform;

import com.github.fluentxml4j.FluentXmlConfigurationException;
import com.github.fluentxml4j.FluentXmlProcessingException;
import com.github.fluentxml4j.internal.transform.filters.PrefixMappingFilterImpl;
import com.github.fluentxml4j.internal.util.StaxUtils;
import com.github.fluentxml4j.serialize.SerializeWithTransformerNode;
import com.github.fluentxml4j.serialize.SerializerConfigurer;
import com.github.fluentxml4j.serialize.SerializerConfigurerAdapter;
import com.github.fluentxml4j.transform.TransformNode;
import org.w3c.dom.Document;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stax.StAXSource;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
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

	TransformNodeImpl(XMLEventReader in)
	{
		this(StaxUtils.newStAXSource(in));
	}

	TransformNodeImpl(XMLStreamReader in)
	{
		this(new StAXSource(in));
	}

	@Override
	public TransformNode withPrefixMapping(String prefix, String newPrefix)
	{
		PrefixMappingFilterImpl filter = new PrefixMappingFilterImpl();
		filter.addPrefixMapping(prefix, newPrefix);
		this.transformationChain.addTransformer(filter);

		return this;
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
			throw new FluentXmlConfigurationException(ex);
		}
	}

	@Override
	public TransformNode withStylesheet(InputStream in)
	{
		return withStylesheet(new StreamSource(in));
	}

	@Override
	public TransformNode withStylesheet(File file)
	{
		return withStylesheet(new StreamSource(file));
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
			throw new FluentXmlProcessingException(ex);
		}
	}

	@Override
	public SerializeWithTransformerNode withSerializer(SerializerConfigurer serializerConfigurer)
	{
		this.transformationChain.addTransformer(serializerConfigurer.getSerializer());

		return new TransformWithSerializerNodeImpl(transformationChain);
	}

	@Override
	public void to(Result out)
	{
		withSerializer(new SerializerConfigurerAdapter()).to(out);
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
	public void to(XMLEventWriter out)
	{
		withSerializer(new SerializerConfigurerAdapter()).to(out);
	}

	@Override
	public void to(XMLStreamWriter out)
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

	@Override
	public org.jdom2.Document toJDOM2Document()
	{
		return this.transformationChain.transformToJDOM2Document();
	}
}
