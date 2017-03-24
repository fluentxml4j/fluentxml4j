package com.github.fluentxml4j;

import com.github.fluentxml4j.serializer.FluentXmlSerializer;
import com.github.fluentxml4j.serializer.SerializeNode;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.w3c.dom.Document;

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

	@Before
	public void setUp()
	{
		fluentXmlInjectionRule.setFluentXmlSerializer(fluentXmlSerializer);
		when(fluentXmlSerializer.serialize(document)).thenReturn(serializeNode);
	}

	@Test
	public void serializeDocument()
	{
		SerializeNode serializeNodeReturned = serialize(document);

		assertThat(serializeNodeReturned, is(this.serializeNode));
	}
}