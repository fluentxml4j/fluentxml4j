package com.github.fluentxml4j;

import org.junit.Test;
import org.w3c.dom.Document;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

import static com.github.fluentxml4j.FluentXml.parse;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FluentXmlTest
{
	@Test
	public void parseDocumentWithDefaultsFromInputStream() throws UnsupportedEncodingException
	{
		byte[] xmlBytes = "<test/>".getBytes("UTF-8");

		Document doc = parse(new ByteArrayInputStream(xmlBytes)).document();

		assertThat(doc.getDocumentElement().getLocalName(), is("test"));
	}
}