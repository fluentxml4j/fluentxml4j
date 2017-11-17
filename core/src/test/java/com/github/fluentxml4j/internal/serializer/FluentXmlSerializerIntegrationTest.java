package com.github.fluentxml4j.internal.serializer;

import com.github.fluentxml4j.junit.XmlResult;
import com.github.fluentxml4j.junit.XmlSource;
import com.github.fluentxml4j.serializer.SerializerConfigurerAdapter;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.xml.bind.JAXBContext;
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
	public static XmlSource documentTestRule = XmlSource.withData("<foo:test xmlns:foo=\"bar\"/>");

	@Rule
	public XmlResult xmlResult = XmlResult.empty();

	@Test
	public void serializeWithDefaultsToString()
	{
		String serializedXml = serialize(documentTestRule.asDocument()).toString();

		assertThat(serializedXml, is("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n<foo:test xmlns:foo=\"bar\"/>\n"));
	}

	@Test
	public void serializeWithDefaultsToWriter()
	{
		StringWriter wr = new StringWriter();

		serialize(documentTestRule.asDocument())
				.to(wr);

		String serializedXml = wr.getBuffer().toString();
		assertThat(serializedXml, is("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n<foo:test xmlns:foo=\"bar\"/>\n"));
	}

	@Test
	public void serializeWithDefaultsToOutputStream()
	{
		ByteArrayOutputStream bytesBufOut = new ByteArrayOutputStream();

		serialize(documentTestRule.asDocument())
				.to(bytesBufOut);

		String serializedXml = new String(bytesBufOut.toByteArray(), Charset.forName("UTF-8"));
		assertThat(serializedXml, is("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n<foo:test xmlns:foo=\"bar\"/>\n"));
	}

	@Test
	public void serializeWithDefaultsToFile()
	{
		serialize(documentTestRule.asDocument())
				.to(xmlResult.asFile());

		String serializedXml = xmlResult.asString();
		assertThat(serializedXml, is("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n<foo:test xmlns:foo=\"bar\"/>\n"));
	}

	@Test
	public void serializeCustomizedToString()
	{
		String serializedXml = serialize(documentTestRule.asDocument())
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

		serialize(documentTestRule.asDocument())
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

		serialize(documentTestRule.asDocument())
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

	@Test
	public void serializeWithJAXBToString() throws Exception
	{
		String serializedXml = serialize(JAXBContext.newInstance(JaxbRoot.class), new JaxbRoot())
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
			.toString();

		assertThat(serializedXml, is("<ns:jaxb-root xmlns:ns=\"uri:ns1\" xmlns=\"uri:ns1\"/>"));
	}
}