package com.github.fluentxml4j;

import com.github.fluentxml4j.internal.serialize.FluentXmlSerializer;
import com.github.fluentxml4j.serialize.SerializeNode;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.w3c.dom.Document;

import javax.xml.bind.JAXBContext;

import static com.github.fluentxml4j.FluentXml.serialize;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FluentXmlSerializeTest
{
	@Rule
	public FluentXmlInjectionRule fluentXmlInjectionRule = new FluentXmlInjectionRule();

	@Mock
	private FluentXmlSerializer fluentXmlSerializer;

	@Mock
	private Document document;

	@Mock
	private SerializeNode serializeNode;

	@Mock
	private JAXBContext jaxbContext;

	@Mock
	private Object object;

	@Before
	public void setUp()
	{
		fluentXmlInjectionRule.setFluentXmlSerializer(fluentXmlSerializer);
	}

	@Test
	public void serializeDocument()
	{
		when(fluentXmlSerializer.serialize(document)).thenReturn(serializeNode);

		SerializeNode serializeNodeReturned = serialize(document);

		assertThat(serializeNodeReturned, is(this.serializeNode));
	}

	@Test
	public void serializeWithJAXB()
	{
		when(fluentXmlSerializer.serialize(jaxbContext, object)).thenReturn(serializeNode);

		SerializeNode serializeNodeReturned = serialize(jaxbContext, object);

		assertThat(serializeNodeReturned, is(this.serializeNode));
	}

}