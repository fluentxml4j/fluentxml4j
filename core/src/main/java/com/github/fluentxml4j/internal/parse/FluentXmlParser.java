package com.github.fluentxml4j.internal.parse;

import com.github.fluentxml4j.FluentXmlProcessingException;
import com.github.fluentxml4j.internal.transform.TransformationChain;
import com.github.fluentxml4j.parse.DocumentBuilderConfigurer;
import com.github.fluentxml4j.parse.DocumentBuilderConfigurerAdapter;
import com.github.fluentxml4j.parse.FromNode;
import com.github.fluentxml4j.parse.ParseNode;
import com.github.fluentxml4j.parse.ParseWithDocumentBuilderNode;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.sax.SAXSource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;

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

	public ParseNode parse(URL in)
	{
		InputSource inputSource = new InputSource(in.toExternalForm());
		return parse(inputSource);
	}

	public ParseNode parse(InputSource inputSource)
	{
		return new ParseNode()
		{
			@Override
			public org.jdom2.Document toJDOM2Document()
			{
				TransformationChain transformationChain = new TransformationChain(new SAXSource(inputSource));
				return transformationChain.transformToJDOM2Document();
			}

			public ParseWithDocumentBuilderNode withDocumentBuilder(DocumentBuilderConfigurer documentBuilderConfigurer)
			{
				return () -> {
					try
					{
						DocumentBuilder documentBuilder = documentBuilderConfigurer.getDocumentBuilder();
						return documentBuilder.parse(inputSource);
					}
					catch (SAXException | IOException ex)
					{
						throw new FluentXmlProcessingException(ex);
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

	public FromNode from(URL url)
	{
		return () -> FluentXmlParser.this.parse(url);
	}
}
