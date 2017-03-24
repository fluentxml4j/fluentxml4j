package com.github.fluentxml4j.parser;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public class FluentXmlParser
{
	public ParseNode parse(InputStream in)
	{
		InputSource inputSource = new InputSource(in);
		return parse(inputSource);
	}

	public ParseNode parse(Reader rd)
	{
		InputSource inputSource = new InputSource(rd);
		return parse(inputSource);
	}

	public ParseNode parse(InputSource inputSource)
	{
		return new ParseNode()
		{
			public ParseWithDocumentBuilderNode withDocumentBuilder(DocumentBuilderConfigurer documentBuilderConfigurer)
			{
				return new ParseWithDocumentBuilderNode()
				{
					@Override
					public Document document()
					{
						try
						{
							DocumentBuilder documentBuilder = documentBuilderConfigurer.getDocumentBuilder();
							return documentBuilder.parse(inputSource);
						}
						catch (SAXException | IOException ex)
						{
							throw new RuntimeException(ex);
						}
					}
				};
			}

			@Override
			public Document document()
			{
				return withDocumentBuilder(new DocumentBuilderConfigurerAdapter()).document();
			}

			@Override
			@Deprecated
			public Document asDocument()
			{
				return document();
			}
		};
	}
}
