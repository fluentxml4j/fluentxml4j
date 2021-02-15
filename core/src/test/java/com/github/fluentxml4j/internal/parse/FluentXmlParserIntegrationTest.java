package com.github.fluentxml4j.internal.parse;

import com.github.fluentxml4j.junit.XmlSource;
import com.github.fluentxml4j.parse.DocumentBuilderConfigurerAdapter;
import org.junit.Rule;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.UnsupportedEncodingException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class FluentXmlParserIntegrationTest
{
	@Rule
	public XmlSource xmlInput = XmlSource.withData("<test/>");

	private FluentXmlParser fluentXmlParser = new FluentXmlParser();

	@Test
	public void parseDocumentWithDefaultsFromInputSource()
	{
		Document doc = fluentXmlParser.parse(new InputSource(xmlInput.asInputStream())).document();

		assertThat(doc.getDocumentElement().getLocalName(), is("test"));
	}

	@Test
	public void parseDocumentWithDefaultsFromFile()
	{
		Document doc = fluentXmlParser.parse(xmlInput.asFile()).document();

		assertThat(doc.getDocumentElement().getLocalName(), is("test"));
	}

	@Test
	public void parseDocumentWithDefaultsFromURL()
	{
		Document doc = fluentXmlParser.parse(xmlInput.asUrl()).document();

		assertThat(doc.getDocumentElement().getLocalName(), is("test"));
	}

	@Test
	public void parseDocumentWithDefaultsFromInputStream()
	{
		Document doc = fluentXmlParser.parse(xmlInput.asInputStream()).document();

		assertThat(doc.getDocumentElement().getLocalName(), is("test"));
	}

	@Test
	public void parseDocumentWithDefaultsFromReader()
	{
		Document doc = fluentXmlParser.parse(xmlInput.asReader("UTF-8")).document();

		assertThat(doc.getDocumentElement().getLocalName(), is("test"));
	}

	@Test
	public void parseDocumentWithConfiguredDocumentBuilderFromInputStream() throws UnsupportedEncodingException
	{
		Document doc = fluentXmlParser.parse(xmlInput.asInputStream()).withDocumentBuilder(new DocumentBuilderConfigurerAdapter()
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
	public void parseDocumentWithDefaultsFromInputStreamViaFrom()
	{
		Document doc = fluentXmlParser.from(xmlInput.asInputStream()).parse().document();

		assertThat(doc.getDocumentElement().getLocalName(), is("test"));
	}

	@Test
	public void parseDocumentWithDefaultsFromInputSourceViaFrom()
	{
		Document doc = fluentXmlParser.from(new InputSource(xmlInput.asInputStream())).parse().document();

		assertThat(doc.getDocumentElement().getLocalName(), is("test"));
	}

	@Test
	public void parseDocumentWithDefaultsFromReaderViaFrom()
	{
		Document doc = fluentXmlParser.from(xmlInput.asReader("UTF-8")).parse().document();

		assertThat(doc.getDocumentElement().getLocalName(), is("test"));
	}

	@Test
	public void parseDocumentWithDefaultsFromFileViaFrom()
	{
		Document doc = fluentXmlParser.from(xmlInput.asFile()).parse().document();

		assertThat(doc.getDocumentElement().getLocalName(), is("test"));
	}

	@Test
	public void parseDocumentWithDefaultsFromURLViaFrom()
	{
		Document doc = fluentXmlParser.from(xmlInput.asUrl()).parse().document();

		assertThat(doc.getDocumentElement().getLocalName(), is("test"));
	}
}
