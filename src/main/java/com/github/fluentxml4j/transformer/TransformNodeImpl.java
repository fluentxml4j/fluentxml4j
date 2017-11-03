package com.github.fluentxml4j.transformer;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

class TransformNodeImpl implements TransformNode
{
	private Source source;
	private List<TransformerHandler> transformers = new ArrayList<>();

	TransformNodeImpl(Source source)
	{
		this.source = source;
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
			this.transformers.add(xsltTransformer);

			return this;
		}
		catch (TransformerException ex)
		{
			throw new RuntimeException(ex);
		}
	}

	@Override
	public Document toDocument()
	{
		try
		{
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			documentBuilderFactory.setNamespaceAware(true);
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.newDocument();
			TransformerHandler firstTransformerOfPipeline = buildPipeline(new DOMResult(document));
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.transform(source, new SAXResult(firstTransformerOfPipeline));
			return document;
		}
		catch (TransformerException | ParserConfigurationException ex)
		{
			throw new RuntimeException(ex);
		}
	}

	private TransformerHandler buildPipeline(Result result) throws TransformerConfigurationException
	{
		if (this.transformers.isEmpty())
		{
			return buildSingleTransformerPipeline(result);
		}
		else
		{
			return buildChainedTransformersPipeline(result);
		}
	}

	private TransformerHandler buildChainedTransformersPipeline(Result result)
	{
		TransformerHandler prevTransformer = null;
		for (TransformerHandler currTransformer : transformers)
		{
			if (prevTransformer != null)
			{
				prevTransformer.setResult(new SAXResult(currTransformer));
			}
			prevTransformer = currTransformer;
		}
		prevTransformer.setResult(result);
		return this.transformers.get(0);
	}

	private TransformerHandler buildSingleTransformerPipeline(Result result) throws TransformerConfigurationException
	{
		SAXTransformerFactory saxTransformerFactory = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
		TransformerHandler transformerHandler = saxTransformerFactory.newTransformerHandler();
		transformerHandler.setResult(result);
		return transformerHandler;
	}
}
