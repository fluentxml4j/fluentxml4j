package com.github.fluentxml4j.internal.serializer;

import com.github.fluentxml4j.DocumentTestRule;
import com.github.fluentxml4j.serializer.SerializerConfigurerAdapter;
import org.junit.ClassRule;
import org.junit.Test;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;

import static com.github.fluentxml4j.FluentXml.serialize;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FluentXmlSerializerIntegrationTest
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
	public void serializeWithDefaultsToWriter()
	{
		StringWriter wr = new StringWriter();

		serialize(documentTestRule.document())
				.to(wr);

		String serializedXml = wr.getBuffer().toString();
		assertThat(serializedXml, is("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n<foo:test xmlns:foo=\"bar\"/>\n"));
	}

	@Test
	public void serializeWithDefaultsToOutputStream()
	{
		ByteArrayOutputStream bytesBufOut = new ByteArrayOutputStream();

		serialize(documentTestRule.document())
				.to(bytesBufOut);

		String serializedXml = new String(bytesBufOut.toByteArray(), Charset.forName("UTF-8"));
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


	@Test
	public void serializeCustomizedToWriter()
	{
		StringWriter wr = new StringWriter();

		serialize(documentTestRule.document())
				.withSerializer(new SerializerConfigurerAdapter()
				{
					@Override
					protected void configure(Transformer transformer)
					{
						super.configure(transformer);
						transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
					}
				})
				.to(wr);

		String serializedXml = wr.getBuffer().toString();
		assertThat(serializedXml, is("<foo:test xmlns:foo=\"bar\"/>\n"));
	}

	@Test
	public void serializeWithCustomizedToOutputStream()
	{
		ByteArrayOutputStream bytesBufOut = new ByteArrayOutputStream();

		serialize(documentTestRule.document())
				.withSerializer(new SerializerConfigurerAdapter()
				{
					@Override
					protected void configure(Transformer transformer)
					{
						super.configure(transformer);
						transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
						transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
						transformer.setOutputProperty(OutputKeys.INDENT, "no");
					}
				})
				.to(bytesBufOut);

		String serializedXml = new String(bytesBufOut.toByteArray(), Charset.forName("UTF-8"));
		assertThat(serializedXml, is("<foo:test xmlns:foo=\"bar\"/>"));
	}

}