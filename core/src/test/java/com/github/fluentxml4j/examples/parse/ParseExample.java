package com.github.fluentxml4j.examples.parse;

import com.github.fluentxml4j.junit.XmlSource;
import org.junit.Rule;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import static com.github.fluentxml4j.FluentXml.from;
import static org.junit.Assert.assertNotNull;

public class ParseExample
{
	@Rule
	public XmlSource xmlSource = XmlSource.withData("<test/>");

	@Test
	public void inputStreamToDocument()
	{
		Document doc = from(xmlSource.asInputStream())
				.parse()
				.document();

		assertNotNull(doc);
	}

	@Test
	public void readerToDocument()
	{
		Document doc = from(xmlSource.asReader("UTF-8"))
				.parse()
				.document();

		assertNotNull(doc);
	}

	@Test
	public void fileToDocument()
	{
		Document doc = from(xmlSource.asFile())
				.parse()
				.document();

		assertNotNull(doc);
	}

	@Test
	public void urlToDocument()
	{
		Document doc = from(xmlSource.asUrl())
				.parse()
				.document();

		assertNotNull(doc);
	}

	@Test
	public void inputSourceToDocument()
	{
		Document doc = from(new InputSource(xmlSource.asInputStream()))
				.parse()
				.document();

		assertNotNull(doc);
	}
}
