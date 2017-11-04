package com.github.fluentxml4j;

import com.github.fluentxml4j.internal.parser.FluentXmlParser;
import com.github.fluentxml4j.internal.serializer.FluentXmlSerializer;
import com.github.fluentxml4j.internal.transformer.FluentXmlTransformer;
import com.github.fluentxml4j.internal.validator.FluentXmlValidator;
import com.github.fluentxml4j.internal.xpath.FluentXPath;
import com.github.fluentxml4j.parser.FromNode;
import com.github.fluentxml4j.parser.ParseNode;
import com.github.fluentxml4j.serializer.SerializeNode;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.io.InputStream;
import java.io.Reader;
import java.util.function.Supplier;

/**
 * This is the main entry point for users of FluentXml4J.
 */
public class FluentXml
{
	static Supplier<FluentXmlParser> fluentXmlParser = supplierFor(new FluentXmlParser());

	static Supplier<FluentXmlSerializer> fluentXmlSerializer = supplierFor(new FluentXmlSerializer());

	static Supplier<FluentXPath> fluentXPath = supplierFor(new FluentXPath());

	static Supplier<FluentXmlTransformer> fluentXmlTransformer = supplierFor(new FluentXmlTransformer());

	static Supplier<FluentXmlValidator> fluentXmlValidator = supplierFor(new FluentXmlValidator());

	private FluentXml()
	{
	}

	private static <T> Supplier<T> supplierFor(T defaultInstance)
	{
		return () -> defaultInstance;
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

	public static com.github.fluentxml4j.xpath.FromNode from(Document doc)
	{
		return fluentXPath.get().from(doc);
	}

	public static SerializeNode serialize(Document doc)
	{
		return fluentXmlSerializer.get().serialize(doc);
	}

	public static com.github.fluentxml4j.transformer.TransformNode transform(Document doc)
	{
		return fluentXmlTransformer.get().transform(doc);
	}

	public static com.github.fluentxml4j.transformer.TransformNode transform(InputStream in)
	{
		return fluentXmlTransformer.get().transform(in);
	}

	public static com.github.fluentxml4j.validator.ValidateNode validate(Document doc)
	{
		return fluentXmlValidator.get().validate(doc);
	}
}
