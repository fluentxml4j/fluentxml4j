package com.github.fluentxml4j.serialize;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SerializerConfigurerAdapterTest
{
	@Mock
	private SAXTransformerFactory saxTransformerFactory;

	@Mock
	private TransformerHandler serializer;

	@Mock
	private Transformer serializerTransformer;

	private SerializerConfigurerAdapter serializerConfigurerAdapter = new SerializerConfigurerAdapter()
	{
		@Override
		protected SAXTransformerFactory buildTransformerFactory()
		{
			return saxTransformerFactory;
		}
	};

	@Before
	public void setUp() throws TransformerConfigurationException
	{
		when(saxTransformerFactory.newTransformerHandler()).thenReturn(serializer);
		when(serializer.getTransformer()).thenReturn(serializerTransformer);
	}

	@Test
	public void getSerializerWithMock() throws Exception
	{
		TransformerHandler serializerBuilt = this.serializerConfigurerAdapter.getSerializer();

		assertThat(serializerBuilt, is(this.serializer));
		verify(serializerTransformer).setOutputProperty(OutputKeys.INDENT, "yes");
	}

	@Test
	public void getSerializer() throws Exception
	{
		TransformerHandler serializerBuilt = this.serializerConfigurerAdapter.getSerializer();

		assertThat(serializerBuilt, not(is(nullValue())));
	}

}