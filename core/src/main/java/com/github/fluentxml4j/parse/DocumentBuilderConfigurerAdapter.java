package com.github.fluentxml4j.parse;

import com.github.fluentxml4j.FluentXmlConfigurationException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class DocumentBuilderConfigurerAdapter implements DocumentBuilderConfigurer
{
	@Override
	public DocumentBuilder getDocumentBuilder()
	{
		try
		{
			DocumentBuilderFactory documentBuilderFactory = buildDocumentBuilderFactory();
			configure(documentBuilderFactory);
			DocumentBuilder documentBuilder = buildDocumentBuilder(documentBuilderFactory);
			configure(documentBuilder);
			return documentBuilder;
		}
		catch (ParserConfigurationException ex)
		{
			throw new FluentXmlConfigurationException(ex);
		}
	}

	protected DocumentBuilder buildDocumentBuilder(DocumentBuilderFactory documentBuilderFactory) throws ParserConfigurationException
	{
		return documentBuilderFactory.newDocumentBuilder();
	}

	protected void configure(DocumentBuilderFactory documentBuilderFactory)
	{
		documentBuilderFactory.setNamespaceAware(true);
	}


	protected void configure(DocumentBuilder documentBuilder)
	{
		// override for customization
	}

	protected DocumentBuilderFactory buildDocumentBuilderFactory()
	{
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		return documentBuilderFactory;
	}


}
