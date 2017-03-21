package com.github.fluentxml4j.parser;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public class FluentXmlParser
{
	public static ParseNode parse(InputStream in)
	{
		InputSource inputSource = new InputSource(in);
		return parse(inputSource);
	}

	public static ParseNode parse(Reader rd)
	{
		InputSource inputSource = new InputSource(rd);
		return parse(inputSource);
	}

	public static ParseNode parse(InputSource inputSource)
	{
		return new ParseNode()
		{
			private boolean namespaceAware = true;

			@Override
			public ParseNode withNamespaces(boolean namespaceAware)
			{
				this.namespaceAware = namespaceAware;
				return this;
			}

			@Override
			public Document asDocument()
			{
				try
				{
					DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
					documentBuilderFactory.setNamespaceAware(this.namespaceAware);
					return documentBuilderFactory.newDocumentBuilder().parse(inputSource);
				}
				catch (ParserConfigurationException | SAXException | IOException ex)
				{
					throw new RuntimeException(ex);
				}
			}
		};
	}
}
