package com.github.fluentxml4j;

import com.github.fluentxml4j.parser.FluentXmlParser;
import com.github.fluentxml4j.parser.ParseNode;
import com.github.fluentxml4j.serializer.FluentXmlSerializer;
import com.github.fluentxml4j.serializer.SerializeNode;
import com.github.fluentxml4j.xpath.FluentXPath;
import com.github.fluentxml4j.xpath.FromNode;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.io.InputStream;
import java.io.Reader;

/**
 * This is the main entry point for users of FluentXml4J.
 */
public class FluentXml
{
	public static ParseNode parse(InputStream in)
	{
		return FluentXmlParser.parse(in);
	}

	public static ParseNode parse(Reader in)
	{
		return FluentXmlParser.parse(in);
	}

	public static ParseNode parse(InputSource in)
	{
		return FluentXmlParser.parse(in);
	}

	public static FromNode from(Document doc)
	{
		return FluentXPath.from(doc);
	}

	public static SerializeNode serialize(Document doc)
	{
		return FluentXmlSerializer.serialize(doc);
	}
}
