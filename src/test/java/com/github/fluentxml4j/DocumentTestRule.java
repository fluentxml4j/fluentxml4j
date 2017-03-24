package com.github.fluentxml4j;


import org.junit.rules.ExternalResource;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;

public class DocumentTestRule extends ExternalResource
{
	private Document document;

	public DocumentTestRule(String xml)
	{
		try
		{
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			documentBuilderFactory.setNamespaceAware(true);
			this.document = documentBuilderFactory.newDocumentBuilder().parse(new InputSource(new StringReader(xml)));
		}
		catch (ParserConfigurationException | SAXException | IOException ex)
		{
			throw new RuntimeException(ex);
		}
	}

	public Document document()
	{
		return document;
	}
}
