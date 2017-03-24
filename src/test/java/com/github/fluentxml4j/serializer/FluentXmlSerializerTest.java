package com.github.fluentxml4j.serializer;

import com.github.fluentxml4j.junit.DocumentTestRule;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.w3c.dom.Document;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

import static com.github.fluentxml4j.parser.FluentXmlParser.parse;
import static com.github.fluentxml4j.serializer.FluentXmlSerializer.serialize;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FluentXmlSerializerTest
{
	@ClassRule
	public static DocumentTestRule documentTestRule = new DocumentTestRule("<test/>");

	@Test
	public void serializeToString()
	{
		String serializedXml = serialize(documentTestRule.document()).toString();

		assertThat(serializedXml, is("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n<test/>\n"));
	}
}