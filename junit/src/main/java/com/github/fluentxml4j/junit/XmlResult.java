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
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilterOutputStream;
import java.io.FilterWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

public class XmlResult extends ExternalResource
{
	private static final Charset UTF8 = Charset.forName("UTF-8");

	private DataHolder dataHolder = EmptyDataHolder.INSTANCE;

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
				XmlResult.this.dataHolder = new BytesDataHolder(bytesOut.toByteArray());
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
				XmlResult.this.dataHolder = new BytesDataHolder(bytesOut.toByteArray());
			}
		};
	}

	public Document asDocument()
	{
		try
		{
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			documentBuilderFactory.setNamespaceAware(true);
			return documentBuilderFactory.newDocumentBuilder().parse(new InputSource(dataHolder.getData()));
		}
		catch (ParserConfigurationException | SAXException | IOException ex)
		{
			throw new RuntimeException(ex);
		}
	}

	public String asString()
	{
		return new String(readAllFrom(dataHolder.getData(), true), UTF8);
	}

	public File asFile()
	{
		try
		{
			File file = File.createTempFile(getClass().getSimpleName(), "asFile");
			this.dataHolder = new FileDataHolder(file);

			return file;
		}
		catch (IOException ex)
		{
			throw new RuntimeException(ex);
		}
	}

	private byte[] readAllFrom(InputStream in, boolean close)
	{
		try
		{
			ByteArrayOutputStream bytesBufOut = new ByteArrayOutputStream();
			byte[] bbuf = new byte[1024];
			int length;
			while ((length = in.read(bbuf)) > -1)
			{
				bytesBufOut.write(bbuf, 0, length);
			}

			return bytesBufOut.toByteArray();
		}
		catch (IOException ex)
		{
			throw new RuntimeException(ex);
		}
		finally
		{
			if (close)
			{
				close(in);
			}
		}
	}

	private void close(InputStream in)
	{
		try
		{
			in.close();
		}
		catch (IOException ex)
		{
			throw new RuntimeException(ex);
		}
	}
}
