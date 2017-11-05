package com.github.fluentxml4j.parser;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DocumentBuilderConfigurerAdapterTest
{
	@Mock
	private DocumentBuilderFactory documentBuilderFactory;

	@Mock
	private DocumentBuilder documentBuilder;

	private DocumentBuilderConfigurerAdapter documentBuilderConfigurerAdapter = new DocumentBuilderConfigurerAdapter()
	{
		@Override
		protected DocumentBuilderFactory buildDocumentBuilderFactory()
		{
			return documentBuilderFactory;
		}

		@Override
		protected void configure(DocumentBuilder documentBuilder)
		{
			documentBuilder.reset();
		}
	};

	@Before
	public void setUp() throws ParserConfigurationException
	{
		when(documentBuilderFactory.newDocumentBuilder()).thenReturn(documentBuilder);
	}

	@Test
	public void getDocumentBuilderWithMock() throws Exception
	{
		DocumentBuilder documentBuilderBuilt = this.documentBuilderConfigurerAdapter.getDocumentBuilder();

		assertThat(documentBuilderBuilt, is(this.documentBuilder));
		verify(documentBuilder).reset();
	}

	@Test
	public void getDocumentBuilder() throws Exception
	{
		DocumentBuilder documentBuilderBuilt = this.documentBuilderConfigurerAdapter.getDocumentBuilder();

		assertThat(documentBuilderBuilt, not(is(nullValue())));
	}

}