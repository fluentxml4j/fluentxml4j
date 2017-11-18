package com.github.fluentxml4j.internal.serialize;

import com.github.fluentxml4j.FluentXmlProcessingException;
import com.github.fluentxml4j.serialize.SerializeWithTransformerNode;
import com.github.fluentxml4j.serialize.SerializerConfigurer;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stax.StAXResult;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;

public class SerializeWithTransformerNodeImpl implements SerializeWithTransformerNode
{
	private final Source source;
	private final SerializerConfigurer serializerConfigurer;

	SerializeWithTransformerNodeImpl(Source source, SerializerConfigurer serializerConfigurer)
	{
		this.source = source;
		this.serializerConfigurer = serializerConfigurer;
	}

	@Override
	public void to(OutputStream out)
	{
		serialize(source, new StreamResult(out));
	}

	@Override
	public void to(Writer out)
	{
		serialize(source, new StreamResult(out));
	}

	@Override
	public void to(XMLStreamWriter out)
	{
		serialize(source, new StAXResult(out));
	}

	@Override
	public void to(Result out)
	{
		serialize(source, out);
	}

	@Override
	public void to(File file)
	{
		serialize(source, new StreamResult(file));
	}

	@Override
	public void to(XMLEventWriter out)
	{
		serialize(source, new StAXResult(out));
	}

	@Override
	public String toString()
	{
		StringWriter wr = new StringWriter();
		serialize(source, new StreamResult(wr));
		return wr.getBuffer().toString();
	}

	@Override
	public byte[] toBytes()
	{
		ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
		serialize(source, new StreamResult(bytesOut));
		return bytesOut.toByteArray();
	}

	private void serialize(Source source, Result result)
	{
		try
		{
			TransformerHandler serializer = serializerConfigurer.getSerializer();
			serializer.getTransformer().transform(source, result);
		}
		catch (TransformerException ex)
		{
			throw new FluentXmlProcessingException(ex);
		}
	}
}
