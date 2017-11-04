package com.github.fluentxml4j.internal.parser;

import com.github.fluentxml4j.parser.DocumentBuilderConfigurerAdapter;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class FluentXmlParserIntegrationTest
{
	private FluentXmlParser fluentXmlParser = new FluentXmlParser();

	@Test
	public void parseDocumentWithDefaultsFromInputSource() throws UnsupportedEncodingException
	{
		byte[] xmlBytes = "<test/>".getBytes("UTF-8");

		Document doc = fluentXmlParser.parse(new InputSource(new ByteArrayInputStream(xmlBytes))).document();

		assertThat(doc.getDocumentElement().getLocalName(), is("test"));
	}

	@Test
	public void parseDocumentWithDefaultsFromInputStream() throws UnsupportedEncodingException
	{
		byte[] xmlBytes = "<test/>".getBytes("UTF-8");

		Document doc = fluentXmlParser.parse(new ByteArrayInputStream(xmlBytes)).document();

		assertThat(doc.getDocumentElement().getLocalName(), is("test"));
	}

	@Test
	public void parseDocumentWithDefaultsFromReader() throws UnsupportedEncodingException
	{
		String xml = "<test/>";

		Document doc = fluentXmlParser.parse(new StringReader(xml)).document();

		assertThat(doc.getDocumentElement().getLocalName(), is("test"));
	}

	@Test
	public void parseDocumentWithConfiguredDocumentBuilderFromInputStream() throws UnsupportedEncodingException
	{
		byte[] xmlBytes = "<test/>".getBytes("UTF-8");

		Document doc = fluentXmlParser.parse(new ByteArrayInputStream(xmlBytes)).withDocumentBuilder(new DocumentBuilderConfigurerAdapter()
		{
			@Override
			protected void configure(DocumentBuilderFactory documentBuilderFactory)
			{
				documentBuilderFactory.setNamespaceAware(false);
			}
		}).document();

		assertThat(doc.getDocumentElement().getLocalName(), is(nullValue()));
		assertThat(doc.getDocumentElement().getNodeName(), is("test"));
	}

	@Test
	public void parseDocumentWithDefaultsFromInputStreamViaFrom() throws UnsupportedEncodingException
	{
		byte[] xmlBytes = "<test/>".getBytes("UTF-8");

		Document doc = fluentXmlParser.from(new ByteArrayInputStream(xmlBytes)).parse().document();

		assertThat(doc.getDocumentElement().getLocalName(), is("test"));
	}

	@Test
	public void parseDocumentWithDefaultsFromInputSourceViaFrom() throws UnsupportedEncodingException
	{
		byte[] xmlBytes = "<test/>".getBytes("UTF-8");

		Document doc = fluentXmlParser.from(new InputSource(new ByteArrayInputStream(xmlBytes))).parse().document();

		assertThat(doc.getDocumentElement().getLocalName(), is("test"));
	}

	@Test
	public void parseDocumentWithDefaultsFromReaderViaFrom() throws UnsupportedEncodingException
	{
		String xml = "<test/>";

		Document doc = fluentXmlParser.from(new StringReader(xml)).parse().document();

		assertThat(doc.getDocumentElement().getLocalName(), is("test"));
	}

}