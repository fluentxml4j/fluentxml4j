package com.github.fluentxml4j.internal.transformer;

import com.github.fluentxml4j.FluentXmlConfigurationException;
import com.github.fluentxml4j.FluentXmlProcessingException;
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
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

class TransformationChain
{
	private Source source;
	private List<TransformerHandler> transformers = new ArrayList<>();

	TransformationChain(Source source)
	{
		this.source = source;
	}

	public void addTransformer(TransformerHandler transformer)
	{
		this.transformers.add(transformer);
	}

	public void transform(Result result)
	{
		try
		{
			TransformerHandler firstTransformerOfPipeline = buildPipeline(result);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.transform(source, new SAXResult(firstTransformerOfPipeline));
		}
		catch (TransformerException ex)
		{
			throw new FluentXmlProcessingException(ex);
		}
	}

	public String transformToString()
	{
		StringWriter strWr = new StringWriter();
		transformTo(strWr);
		return strWr.getBuffer().toString();
	}

	public void transformTo(OutputStream out)
	{
		transform(new StreamResult(out));
	}

	public void transformTo(Writer out)
	{
		transform(new StreamResult(out));
	}

	public Document transformToDocument()
	{
		try
		{
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			documentBuilderFactory.setNamespaceAware(true);
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.newDocument();

			transform(new DOMResult(document));
			return document;
		}
		catch (ParserConfigurationException ex)
		{
			throw new FluentXmlConfigurationException(ex);
		}

	}

	private TransformerHandler buildPipeline(Result result)
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
		TransformerHandler firstTransformer = null;
		for (TransformerHandler currTransformer : transformers)
		{
			if (firstTransformer == null)
			{
				firstTransformer = currTransformer;
			}

			if (prevTransformer != null)
			{
				prevTransformer.setResult(new SAXResult(currTransformer));
			}
			prevTransformer = currTransformer;
		}

		if (prevTransformer == null)
		{
			throw new IllegalStateException("Internal problem: No previous transformer.");
		}

		prevTransformer.setResult(result);

		if (firstTransformer == null)
		{
			throw new IllegalStateException("Internal problem: No first transformer.");
		}

		return firstTransformer;
	}

	private TransformerHandler buildSingleTransformerPipeline(Result result)
	{
		try
		{
			SAXTransformerFactory saxTransformerFactory = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
			TransformerHandler transformerHandler = saxTransformerFactory.newTransformerHandler();
			transformerHandler.setResult(result);
			return transformerHandler;
		}
		catch (TransformerConfigurationException ex)
		{
			throw new FluentXmlConfigurationException(ex);
		}
	}
}
