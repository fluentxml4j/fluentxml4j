package com.github.fluentxml4j.internal.parser;

import com.github.fluentxml4j.FluentXmlProcessingException;
import com.github.fluentxml4j.parser.DocumentBuilderConfigurer;
import com.github.fluentxml4j.parser.DocumentBuilderConfigurerAdapter;
import com.github.fluentxml4j.parser.FromNode;
import com.github.fluentxml4j.parser.ParseNode;
import com.github.fluentxml4j.parser.ParseWithDocumentBuilderNode;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import java.io.File;
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

	public ParseNode parse(File in)
	{
		InputSource inputSource = new InputSource(in.getAbsolutePath());
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
							throw new FluentXmlProcessingException(ex);
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

	public FromNode from(InputSource in)
	{
		return () -> FluentXmlParser.this.parse(in);
	}

	public FromNode from(InputStream in)
	{
		return () -> FluentXmlParser.this.parse(in);
	}

	public FromNode from(Reader in)
	{
		return () -> FluentXmlParser.this.parse(in);
	}

	public FromNode from(File file)
	{
		return () -> FluentXmlParser.this.parse(file);
	}
}
