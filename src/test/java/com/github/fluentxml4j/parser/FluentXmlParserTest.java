package com.github.fluentxml4j.parser;

import org.junit.Test;
import org.w3c.dom.Document;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

import static com.github.fluentxml4j.parser.FluentXmlParser.parse;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class FluentXmlParserTest
{
	@Test
	public void parseDocumentFromInputStream() throws UnsupportedEncodingException
	{
		byte[] xmlBytes = "<test/>".getBytes("UTF-8");

		Document doc = parse(new ByteArrayInputStream(xmlBytes)).asDocument();

		assertThat(doc.getDocumentElement().getLocalName(), is("test"));
	}

}