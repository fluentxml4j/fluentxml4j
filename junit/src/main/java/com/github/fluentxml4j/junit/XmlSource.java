package com.github.fluentxml4j.junit;

import org.junit.rules.ExternalResource;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
import java.nio.charset.Charset;

public class XmlSource extends ExternalResource
{
	private String xml;

	public static XmlSource withData(String xml)
	{
		return new XmlSource(xml);
	}

	public static XmlSource withDataFrom(Class<?> clazz, String path)
	{
		try (InputStream in = clazz.getResourceAsStream(path))
		{
			byte[] bytes = readBytes(in);

			return new XmlSource(new String(bytes, "UTF-8"));
		}
		catch (IOException ex)
		{
			throw new RuntimeException(ex);
		}
	}

	private static byte[] readBytes(InputStream in) throws IOException
	{
		byte[] bbuf = new byte[1024];
		int lenght;
		ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
		while ((lenght = in.read(bbuf)) != -1)
		{
			bytesOut.write(bbuf, 0, lenght);
		}
		return bytesOut.toByteArray();
	}

	private XmlSource(String xml)
	{
		this.xml = xml;
	}

	public URL asUrl()
	{
		File tempFile = exportXmlToTempFile();

		return toURL(tempFile);
	}

	public InputStream asInputStream()
	{
		return new ByteArrayInputStream(this.xml.getBytes(Charset.forName("UTF-8")));
	}

	public Document asDocument()
	{
		try
		{
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			documentBuilderFactory.setNamespaceAware(true);
			return documentBuilderFactory.newDocumentBuilder().parse(new InputSource(new StringReader(xml)));
		}
		catch (ParserConfigurationException | SAXException | IOException ex)
		{
			throw new RuntimeException(ex);
		}
	}

	public byte[] asBytes(String charset)
	{
		try
		{
			return xml.getBytes(charset);
		}
		catch (IOException ex)
		{
			throw new RuntimeException(ex);
		}
	}

	public File asFile()
	{
		return exportXmlToTempFile();
	}

	public Reader asReader()
	{
		return new StringReader(this.xml);
	}

	private File exportXmlToTempFile()
	{
		File tempFile = getXmlTempFile();

		writeTo(tempFile);

		return tempFile;
	}

	private URL toURL(File tempFile)
	{
		try
		{
			return tempFile.toURI().toURL();
		}
		catch (IOException ex)
		{
			throw new RuntimeException(ex);
		}
	}

	private void writeTo(File tempFile)
	{
		try (FileOutputStream fileOut = new FileOutputStream(tempFile))
		{
			fileOut.write(this.xml.getBytes(Charset.forName("UTF-8")));
		}
		catch (IOException ex)
		{
			throw new RuntimeException(ex);
		}
	}

	private File getXmlTempFile()
	{
		try
		{
			File tempFile = File.createTempFile(getClass().getSimpleName(), "document.xml");
			tempFile.deleteOnExit();
			return tempFile;
		}
		catch (IOException ex)
		{
			throw new RuntimeException(ex);
		}
	}

	public String asString()
	{
		return this.xml;
	}

	public XMLStreamReader asXMLStreamReader()
	{
		try
		{
			return XMLInputFactory.newFactory().createXMLStreamReader(new StringReader(this.xml));
		}
		catch (XMLStreamException e)
		{
			throw new IllegalStateException(e);
		}
	}

	public XMLEventReader asXMLEventReader()
	{
		try
		{
			return XMLInputFactory.newFactory().createXMLEventReader(new StringReader(this.xml));
		}
		catch (XMLStreamException e)
		{
			throw new IllegalStateException(e);
		}
	}
}
