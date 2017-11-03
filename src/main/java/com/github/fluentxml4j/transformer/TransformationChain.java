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
			throw new RuntimeException(ex);
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
			throw new RuntimeException(ex);
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
			throw new RuntimeException(ex);
		}
	}
}
