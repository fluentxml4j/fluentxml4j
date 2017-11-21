package com.github.fluentxml4j.internal.transform;

import com.github.fluentxml4j.FluentXmlConfigurationException;
import com.github.fluentxml4j.FluentXmlProcessingException;
import org.jdom2.transform.JDOMResult;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamWriter;
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
import javax.xml.transform.stax.StAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.ValidatorHandler;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class TransformationChain
{
	private Source source;
	private List<TransformerAdapter> transformers = new ArrayList<>();

	public TransformationChain(Source source)
	{
		this.source = source;
	}

	public void addTransformer(TransformerHandler transformer)
	{
		this.transformers.add(new TrAXTransformerAdapter(transformer));
	}

	public void addTransformer(ValidatorHandler transformer)
	{
		this.transformers.add(new ValidatorAdapter(transformer));
	}

	public void transform()
	{
		transformTo((Result) null);
	}

	public void transformTo(Result result)
	{
		try
		{
			Result firstTransformerOfPipeline = buildPipeline(result);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.transform(source, firstTransformerOfPipeline);
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
		transformTo(new StreamResult(out));
	}

	public void transformTo(Writer out)
	{
		transformTo(new StreamResult(out));
	}

	public Document transformToDocument()
	{
		try
		{
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			documentBuilderFactory.setNamespaceAware(true);
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.newDocument();

			transformTo(new DOMResult(document));
			return document;
		}
		catch (ParserConfigurationException ex)
		{
			throw new FluentXmlConfigurationException(ex);
		}
	}

	public org.jdom2.Document transformToJDOM2Document()
	{
		JDOMResult result = new JDOMResult();
		transformTo(result);
		return result.getDocument();
	}

	private Result buildPipeline(Result result)
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

	private Result buildChainedTransformersPipeline(Result result)
	{
		TransformerAdapter prevTransformer = null;
		TransformerAdapter firstTransformer = null;
		for (TransformerAdapter currTransformer : transformers)
		{
			if (firstTransformer == null)
			{
				firstTransformer = currTransformer;
			}

			if (prevTransformer != null)
			{
				prevTransformer.setNext(currTransformer.asResult());
			}
			prevTransformer = currTransformer;
		}

		if (prevTransformer == null)
		{
			throw new IllegalStateException("Internal problem: No previous transformer.");
		}

		if (result != null)
		{
			prevTransformer.setNext(result);
		}

		return firstTransformer.asResult();
	}

	private Result buildSingleTransformerPipeline(Result result)
	{
		try
		{
			SAXTransformerFactory saxTransformerFactory = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
			TransformerHandler transformerHandler = saxTransformerFactory.newTransformerHandler();
			if (result != null)
			{
				transformerHandler.setResult(result);
			}
			return new SAXResult(transformerHandler);
		}
		catch (TransformerConfigurationException ex)
		{
			throw new FluentXmlConfigurationException(ex);
		}
	}

	public void transformTo(XMLStreamWriter out)
	{
		transformTo(new StAXResult(out));
	}

	public void transformTo(XMLEventWriter out)
	{
		transformTo(new StAXResult(out));
	}

	public void transformTo(File file)
	{
		transformTo(new StreamResult(file));
	}

	public byte[] transformToBytes()
	{
		ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
		transformTo(new StreamResult(bytesOut));
		return bytesOut.toByteArray();
	}

	private static class TrAXTransformerAdapter implements TransformationChain.TransformerAdapter
	{
		private TransformerHandler transformerHandler;

		public TrAXTransformerAdapter(TransformerHandler transformerHandler)
		{
			this.transformerHandler = transformerHandler;
		}

		@Override
		public Result asResult()
		{
			return new SAXResult(this.transformerHandler);
		}

		@Override
		public void setNext(Result result)
		{
			this.transformerHandler.setResult(result);
		}
	}

	private static class ValidatorAdapter implements TransformationChain.TransformerAdapter
	{
		private ValidatorHandler transformerHandler;

		public ValidatorAdapter(ValidatorHandler transformerHandler)
		{
			this.transformerHandler = transformerHandler;
		}

		@Override
		public Result asResult()
		{
			return new SAXResult(this.transformerHandler);
		}

		@Override
		public void setNext(Result result)
		{
			if (!(result instanceof SAXResult))
			{
				throw new IllegalArgumentException("Only SAX result is supported.");
			}

			this.transformerHandler.setContentHandler(((SAXResult) result).getHandler());
		}
	}


	private interface TransformerAdapter
	{
		Result asResult();

		void setNext(Result result);
	}
}
