package com.github.fluentxml4j.junit;

import org.junit.rules.ExternalResource;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FilterOutputStream;
import java.io.FilterWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

public class XmlResult extends ExternalResource
{
	private static final Charset UTF8 = Charset.forName("UTF-8");

	private byte[] data;

	public static XmlResult empty()
	{
		return new XmlResult();
	}

	private XmlResult()
	{
	}

	public XMLStreamWriter getXMLStreamWriter(AutoFlushPolicy autoFlush)
	{
		try
		{
			return XMLStreamWriterProxy.proxyFor(XMLOutputFactory.newFactory().createXMLStreamWriter(getOutputStream()), autoFlush == AutoFlushPolicy.AUTO_FLUSH);
		}
		catch (XMLStreamException ex)
		{
			throw new IllegalStateException(ex);
		}
	}

	public XMLEventWriter getXMLEventWriter(AutoFlushPolicy autoFlush)
	{
		try
		{
			return XMLEventWriterProxy.proxyFor(XMLOutputFactory.newFactory().createXMLEventWriter(getOutputStream()), autoFlush == AutoFlushPolicy.AUTO_FLUSH);
		}
		catch (XMLStreamException ex)
		{
			throw new IllegalStateException(ex);
		}
	}

	public OutputStream getOutputStream()
	{
		return new FilterOutputStream(new ByteArrayOutputStream())
		{
			@Override
			public void flush() throws IOException
			{
				super.flush();
				publishData();
			}

			@Override
			public void close() throws IOException
			{
				super.close();
				publishData();
			}

			private void publishData()
			{
				ByteArrayOutputStream bytesOut = (ByteArrayOutputStream) out;
				XmlResult.this.data = bytesOut.toByteArray();
			}
		};
	}

	public Writer getWriter()
	{
		ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
		return new FilterWriter(new OutputStreamWriter(bytesOut, UTF8))
		{
			@Override
			public void flush() throws IOException
			{
				super.flush();
				publishData();
			}

			@Override
			public void close() throws IOException
			{
				super.close();
				publishData();
			}

			private void publishData()
			{
				XmlResult.this.data = bytesOut.toByteArray();
			}
		};
	}

	public Document asDocument()
	{
		try
		{
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			documentBuilderFactory.setNamespaceAware(true);
			return documentBuilderFactory.newDocumentBuilder().parse(new InputSource(new ByteArrayInputStream(data)));
		}
		catch (ParserConfigurationException | SAXException | IOException ex)
		{
			throw new RuntimeException(ex);
		}
	}

	public String asString()
	{
		return new String(this.data, UTF8);
	}
}
