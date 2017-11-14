package com.github.fluentxml4j.internal.transformer.filters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.xml.sax.Attributes;
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
	private static final char[] CHARS = "test".toCharArray();

	@Mock
	private ContentHandler contentHandler;
	@Mock
	private XMLStreamWriter xmlStreamWriter;
	@Mock
	private Attributes attributes;

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

	@Test
	public void delegatesPerDefault() throws SAXException
	{
		filter.setResult(new SAXResult(this.contentHandler));

		filter.startDocument();
		verify(this.contentHandler).startDocument();

		filter.startPrefixMapping("prefix", "nsURI");
		verify(this.contentHandler).startPrefixMapping("prefix", "nsURI");

		filter.startElement("nsURI", "localName", "qName", attributes);
		verify(this.contentHandler).startElement("nsURI", "localName", "qName", attributes);

		filter.characters(CHARS, 100, 200);
		verify(this.contentHandler).characters(CHARS, 100, 200);

		filter.ignorableWhitespace(CHARS, 300, 400);
		verify(this.contentHandler).ignorableWhitespace(CHARS, 300, 400);

		filter.processingInstruction("target", "data");
		verify(this.contentHandler).processingInstruction("target", "data");

		filter.skippedEntity("entity");
		verify(this.contentHandler).skippedEntity("entity");

		filter.endElement("nsURI", "localName", "qName");
		verify(this.contentHandler).endElement("nsURI", "localName", "qName");

		filter.endPrefixMapping("prefix");
		verify(this.contentHandler).endPrefixMapping("prefix");

		filter.endDocument();
		verify(this.contentHandler).endDocument();
	}
}