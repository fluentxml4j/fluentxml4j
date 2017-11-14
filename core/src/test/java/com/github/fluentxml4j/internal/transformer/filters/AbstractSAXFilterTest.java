package com.github.fluentxml4j.internal.transformer.filters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stax.StAXResult;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AbstractSAXFilterTest
{
	@Mock
	private ContentHandler contentHandler;
	@Mock
	private XMLStreamWriter xmlStreamWriter;

	private AbstractSAXFilter filter = new AbstractSAXFilter()
	{
	};

	@Test
	public void adaptsToSAXResult() throws SAXException
	{
		filter.setResult(new SAXResult(this.contentHandler));

		filter.startDocument();

		verify(this.contentHandler).startDocument();
	}

	@Test
	public void adaptsToStAXResult() throws SAXException, XMLStreamException
	{
		filter.setResult(new StAXResult(this.xmlStreamWriter));

		filter.endDocument();

		verify(this.xmlStreamWriter).writeEndDocument();
	}
}