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
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

public class XmlSource extends ExternalResource
{
	private byte[] data;

	public static XmlSource withData(String xml)
	{
		try
		{
			return new XmlSource(xml.getBytes("UTF-8"));
		}
		catch (IOException ex)
		{
			throw new RuntimeException(ex);
		}
	}

	public static XmlSource withData(byte[] xml)
	{
		return new XmlSource(xml);
	}

	public static XmlSource withDataFrom(Class<?> clazz, String path)
	{
		try (InputStream in = clazz.getResourceAsStream(path))
		{
			byte[] bytes = readBytes(in);

			return new XmlSource(bytes);
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

	private XmlSource(byte[] data)
	{
		this.data = data;
	}

	public URL asUrl()
	{
		File tempFile = exportXmlToTempFile();

		return toURL(tempFile);
	}

	public InputStream asInputStream()
	{
		return new ByteArrayInputStream(this.data);
	}

	public Document asDocument()
	{
		try
		{
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			documentBuilderFactory.setNamespaceAware(true);
			return documentBuilderFactory.newDocumentBuilder().parse(new InputSource(asInputStream()));
		}
		catch (ParserConfigurationException | SAXException | IOException ex)
		{
			throw new RuntimeException(ex);
		}
	}

	public byte[] asBytes()
	{
		return this.data;
	}

	public File asFile()
	{
		return exportXmlToTempFile();
	}

	public Reader asReader(String charSet)
	{
		try
		{
			return new InputStreamReader(asInputStream(), charSet);
		}
		catch (IOException ex)
		{
			throw new RuntimeException(ex);
		}
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
			fileOut.write(this.data);
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

	public String asString(String charSet)
	{
		try
		{
			return new String(this.data, charSet);
		}
		catch (IOException ex)
		{
			throw new RuntimeException(ex);
		}
	}

	public String asString()
	{
		return asString("UTF-8");
	}

	public XMLStreamReader asXMLStreamReader()
	{
		try
		{
			return XMLInputFactory.newFactory().createXMLStreamReader(asInputStream());
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
			return XMLInputFactory.newFactory().createXMLEventReader(asInputStream());
		}
		catch (XMLStreamException e)
		{
			throw new IllegalStateException(e);
		}
	}
}
