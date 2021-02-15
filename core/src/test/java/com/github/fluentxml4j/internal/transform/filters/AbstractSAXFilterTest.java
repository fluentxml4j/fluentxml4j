package com.github.fluentxml4j.internal.transform.filters;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DefaultHandler2;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.ErrorListener;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stax.StAXResult;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AbstractSAXFilterTest
{
	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	private static final char[] CHARS = "test".toCharArray();

	@Mock
	private Source source;
	@Mock
	private Result result;
	@Mock
	private DefaultHandler2 contentHandler;
	@Mock
	private XMLStreamWriter xmlStreamWriter;
	@Mock
	private Attributes attributes;
	@Mock
	private ErrorListener errorListener;

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
	public void delegatesToContentHandlerPerDefault() throws SAXException
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

	}

	@Test
	public void delegatesToLexicalHandlerPerDefault() throws SAXException
	{
		filter.setResult(new SAXResult(this.contentHandler));

		filter.startDTD("name", "publicId", "systemId");
		verify(contentHandler).startDTD("name", "publicId", "systemId");
		filter.endDTD();
		verify(contentHandler).endDTD();
		filter.startCDATA();
		verify(contentHandler).startCDATA();
		filter.endCDATA();
		verify(contentHandler).endCDATA();
		filter.startEntity("name");
		verify(contentHandler).startEntity("name");
		filter.endEntity("name");
		verify(contentHandler).endEntity("name");
		filter.comment(CHARS, 800, 900);
		verify(contentHandler).comment(CHARS, 800, 900);
	}

	@Test
	public void delegatesToDtdHandlerPerDefault() throws SAXException
	{
		filter.setResult(new SAXResult(this.contentHandler));

		filter.notationDecl("name", "publicId", "systemId");
		verify(contentHandler).notationDecl("name", "publicId", "systemId");
		filter.unparsedEntityDecl("name", "publicId", "systemId", "notationName");
		verify(contentHandler).unparsedEntityDecl("name", "publicId", "systemId", "notationName");
	}

	@Test
	public void systemId()
	{
		filter.setSystemId("aSystemId");

		assertThat(filter.getSystemId(), is("aSystemId"));
	}

	@Test
	public void noParametersSupported()
	{
		expectedException.expect(IllegalArgumentException.class);

		filter.setParameter("name", "value");
	}

	@Test
	public void clearParametersSupportedNoException()
	{
		filter.clearParameters();
	}

	@Test
	public void getParameterReturnsNull()
	{
		Object value = filter.getParameter("name");

		assertThat(value, is(nullValue()));
	}

	@Test
	public void transformNotSupported() throws TransformerException
	{
		expectedException.expect(UnsupportedOperationException.class);

		this.filter.transform(source, result);
	}

	@Test
	public void noOutputPropertySupported()
	{
		expectedException.expect(IllegalArgumentException.class);

		filter.setOutputProperty("name", "value");
	}

	@Test
	public void getOutputPropertyReturnsNull()
	{
		Object value = filter.getOutputProperty("name");

		assertThat(value, is(nullValue()));
	}

	@Test
	public void errorListenerSupported()
	{
		this.filter.setErrorListener(errorListener);

		assertThat(this.filter.getErrorListener(), is(this.errorListener));
	}
}
