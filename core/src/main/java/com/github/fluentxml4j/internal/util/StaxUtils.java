package com.github.fluentxml4j.internal.util;

import com.github.fluentxml4j.FluentXmlProcessingException;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.stax.StAXSource;
import java.io.OutputStream;

public class StaxUtils
{
	private StaxUtils()
	{
	}

	public static StAXSource newStAXSource(XMLEventReader in)
	{
		try
		{
			return new StAXSource(in);
		}
		catch (XMLStreamException ex)
		{
			throw new FluentXmlProcessingException(ex);
		}
	}

	public static XMLStreamWriter newXMLStreamWriter(OutputStream out)
	{
		try
		{
			return XMLOutputFactory.newFactory().createXMLStreamWriter(out);
		}
		catch (XMLStreamException ex)
		{
			throw new IllegalStateException(ex);
		}
	}

	public static XMLEventWriter newXMLEventWriter(OutputStream out)
	{
		try
		{
			return XMLOutputFactory.newFactory().createXMLEventWriter(out);
		}
		catch (XMLStreamException ex)
		{
			throw new IllegalStateException(ex);
		}
	}


}
