package com.github.fluentxml4j.parser;

import org.junit.Test;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

import static com.github.fluentxml4j.parser.FluentXmlParser.parse;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

public class FluentXmlParserTest
{
	@Test
	public void parseDocumentWithDefaultsFromInputStream() throws UnsupportedEncodingException
	{
		byte[] xmlBytes = "<test/>".getBytes("UTF-8");

		Document doc = parse(new ByteArrayInputStream(xmlBytes)).document();

		assertThat(doc.getDocumentElement().getLocalName(), is("test"));
	}

	@Test
	public void parseDocumentWithConfiguredDocumentBuilderFromInputStream() throws UnsupportedEncodingException
	{
		byte[] xmlBytes = "<test/>".getBytes("UTF-8");

		Document doc = parse(new ByteArrayInputStream(xmlBytes)).withDocumentBuilder(new DocumentBuilderConfigurerAdapter()
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

}