package com.github.fluentxml4j.internal.parser;

import com.github.fluentxml4j.DocumentTestRule;
import com.github.fluentxml4j.parser.DocumentBuilderConfigurerAdapter;
import org.junit.Rule;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.UnsupportedEncodingException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class FluentXmlParserIntegrationTest
{
	@Rule
	public DocumentTestRule xmlInput = new DocumentTestRule("<test/>");

	private FluentXmlParser fluentXmlParser = new FluentXmlParser();

	@Test
	public void parseDocumentWithDefaultsFromInputSource()
	{
		Document doc = fluentXmlParser.parse(new InputSource(xmlInput.inputStream())).document();

		assertThat(doc.getDocumentElement().getLocalName(), is("test"));
	}

	@Test
	public void parseDocumentWithDefaultsFromFile()
	{
		Document doc = fluentXmlParser.parse(xmlInput.file()).document();

		assertThat(doc.getDocumentElement().getLocalName(), is("test"));
	}

	@Test
	public void parseDocumentWithDefaultsFromURL()
	{
		Document doc = fluentXmlParser.parse(xmlInput.url()).document();

		assertThat(doc.getDocumentElement().getLocalName(), is("test"));
	}

	@Test
	public void parseDocumentWithDefaultsFromInputStream()
	{
		Document doc = fluentXmlParser.parse(xmlInput.inputStream()).document();

		assertThat(doc.getDocumentElement().getLocalName(), is("test"));
	}

	@Test
	public void parseDocumentWithDefaultsFromReader()
	{
		Document doc = fluentXmlParser.parse(xmlInput.reader()).document();

		assertThat(doc.getDocumentElement().getLocalName(), is("test"));
	}

	@Test
	public void parseDocumentWithConfiguredDocumentBuilderFromInputStream() throws UnsupportedEncodingException
	{
		Document doc = fluentXmlParser.parse(xmlInput.inputStream()).withDocumentBuilder(new DocumentBuilderConfigurerAdapter()
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
		Document doc = fluentXmlParser.from(xmlInput.inputStream()).parse().document();

		assertThat(doc.getDocumentElement().getLocalName(), is("test"));
	}

	@Test
	public void parseDocumentWithDefaultsFromInputSourceViaFrom()
	{
		Document doc = fluentXmlParser.from(new InputSource(xmlInput.inputStream())).parse().document();

		assertThat(doc.getDocumentElement().getLocalName(), is("test"));
	}

	@Test
	public void parseDocumentWithDefaultsFromReaderViaFrom()
	{
		Document doc = fluentXmlParser.from(xmlInput.reader()).parse().document();

		assertThat(doc.getDocumentElement().getLocalName(), is("test"));
	}

	@Test
	public void parseDocumentWithDefaultsFromFileViaFrom()
	{
		Document doc = fluentXmlParser.from(xmlInput.file()).parse().document();

		assertThat(doc.getDocumentElement().getLocalName(), is("test"));
	}

	@Test
	public void parseDocumentWithDefaultsFromURLViaFrom()
	{
		Document doc = fluentXmlParser.from(xmlInput.url()).parse().document();

		assertThat(doc.getDocumentElement().getLocalName(), is("test"));
	}
}