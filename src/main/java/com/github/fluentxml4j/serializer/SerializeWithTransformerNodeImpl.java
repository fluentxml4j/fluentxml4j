package com.github.fluentxml4j.serializer;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;

class SerializeWithTransformerNodeImpl implements SerializeWithTransformerNode
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
	public String toString()
	{
		StringWriter wr = new StringWriter();
		serialize(source, new StreamResult(wr));
		return wr.getBuffer().toString();
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
			throw new RuntimeException(ex);
		}
	}
}
