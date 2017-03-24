package com.github.fluentxml4j.serializer;

import com.github.fluentxml4j.junit.DocumentTestRule;
import org.junit.ClassRule;
import org.junit.Test;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;

import static com.github.fluentxml4j.serializer.FluentXmlSerializer.serialize;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FluentXmlSerializerTest
{
	@ClassRule
	public static DocumentTestRule documentTestRule = new DocumentTestRule("<foo:test xmlns:foo=\"bar\"/>");

	@Test
	public void serializeWithDefaultsToString()
	{
		String serializedXml = serialize(documentTestRule.document()).toString();

		assertThat(serializedXml, is("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n<foo:test xmlns:foo=\"bar\"/>\n"));
	}

	@Test
	public void serializeCustomizedToString()
	{
		String serializedXml = serialize(documentTestRule.document())
				.withSerializer(new SerializerConfigurerAdapter()
				{
					@Override
					protected void configure(Transformer transformer)
					{
						super.configure(transformer);
						transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
					}
				})
				.toString();

		assertThat(serializedXml, is("<foo:test xmlns:foo=\"bar\"/>\n"));
	}

}