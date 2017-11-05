package com.github.fluentxml4j;


import org.junit.rules.ExternalResource;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
import java.nio.charset.Charset;

public class DocumentTestRule extends ExternalResource
{
	private String xml;

	public DocumentTestRule(String xml)
	{
		this.xml = xml;
	}

	public URL url()
	{
		File tempFile = exportXmlToTempFile();

		return toURL(tempFile);
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
			return File.createTempFile(getClass().getSimpleName(), "document.xml");
		}
		catch (IOException ex)
		{
			throw new RuntimeException(ex);
		}
	}

	public InputStream inputStream()
	{
		return new ByteArrayInputStream(this.xml.getBytes(Charset.forName("UTF-8")));
	}

	public Document document()
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

	public byte[] bytes(String charset)
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

	public File file()
	{
		return exportXmlToTempFile();
	}

	public Reader reader()
	{
		return new StringReader(this.xml);
	}
}
