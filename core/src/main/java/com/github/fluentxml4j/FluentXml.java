package com.github.fluentxml4j;

import com.github.fluentxml4j.internal.parse.FluentXmlParser;
import com.github.fluentxml4j.internal.serialize.FluentXmlSerializer;
import com.github.fluentxml4j.internal.transform.FluentXmlTransformer;
import com.github.fluentxml4j.internal.query.FluentQuery;
import com.github.fluentxml4j.parse.FromNode;
import com.github.fluentxml4j.parse.ParseNode;
import com.github.fluentxml4j.serialize.SerializeNode;
import com.github.fluentxml4j.transform.TransformNode;
import com.github.fluentxml4j.query.QueryFromNode;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import javax.xml.bind.JAXBContext;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.util.function.Supplier;

/**
 * This is the main entry point for users of FluentXml4J.
 */
public class FluentXml
{
	static Supplier<FluentXmlParser> fluentXmlParser = supplierFor(new FluentXmlParser());

	static Supplier<FluentXmlSerializer> fluentXmlSerializer = supplierFor(new FluentXmlSerializer());

	static Supplier<FluentQuery> fluentQuery = supplierFor(new FluentQuery());

	static Supplier<FluentXmlTransformer> fluentXmlTransformer = supplierFor(new FluentXmlTransformer());

	private FluentXml()
	{
	}

	private static <T> Supplier<T> supplierFor(T defaultInstance)
	{
		return () -> defaultInstance;
	}

	public static ParseNode parse(File in)
	{
		return fluentXmlParser.get().parse(in);
	}

	public static ParseNode parse(InputStream in)
	{
		return fluentXmlParser.get().parse(in);
	}

	public static ParseNode parse(Reader in)
	{
		return fluentXmlParser.get().parse(in);
	}

	public static ParseNode parse(InputSource in)
	{
		return fluentXmlParser.get().parse(in);
	}

	public static ParseNode parse(URL in)
	{
		return fluentXmlParser.get().parse(in);
	}

	public static FromNode from(File in)
	{
		return () -> fluentXmlParser.get().parse(in);
	}

	public static FromNode from(InputSource in)
	{
		return () -> fluentXmlParser.get().parse(in);
	}

	public static FromNode from(InputStream in)
	{
		return () -> fluentXmlParser.get().parse(in);
	}

	public static FromNode from(Reader in)
	{
		return () -> fluentXmlParser.get().parse(in);
	}

	public static FromNode from(URL in)
	{
		return () -> fluentXmlParser.get().parse(in);
	}

	public static QueryFromNode from(Document doc)
	{
		return fluentQuery.get().from(doc);
	}

	public static QueryFromNode from(Node node)
	{
		return fluentQuery.get().from(node);
	}

	public static SerializeNode serialize(Document doc)
	{
		return fluentXmlSerializer.get().serialize(doc);
	}

	public static SerializeNode serialize(JAXBContext jaxbContext, Object object)
	{
		return fluentXmlSerializer.get().serialize(jaxbContext, object);
	}

	public static TransformNode transform(URL url)
	{
		return fluentXmlTransformer.get().transform(url);
	}

	public static TransformNode transform(File file)
	{
		return fluentXmlTransformer.get().transform(file);
	}

	public static TransformNode transform(Document doc)
	{
		return fluentXmlTransformer.get().transform(doc);
	}

	public static TransformNode transform(InputStream in)
	{
		return fluentXmlTransformer.get().transform(in);
	}

	public static TransformNode transform(XMLStreamReader in)
	{
		return fluentXmlTransformer.get().transform(in);
	}

	public static TransformNode transform(XMLEventReader in)
	{
		return fluentXmlTransformer.get().transform(in);
	}

	public static TransformNode transform(JAXBContext jaxbContext, Object object)
	{
		return fluentXmlTransformer.get().transform(jaxbContext, object);
	}
}
