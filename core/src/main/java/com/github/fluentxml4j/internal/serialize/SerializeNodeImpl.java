package com.github.fluentxml4j.internal.serialize;

import com.github.fluentxml4j.serialize.SerializeNode;
import com.github.fluentxml4j.serialize.SerializeWithTransformerNode;
import com.github.fluentxml4j.serialize.SerializerConfigurer;
import com.github.fluentxml4j.serialize.SerializerConfigurerAdapter;

import javax.xml.transform.Source;
import java.io.File;
import java.io.OutputStream;
import java.io.Writer;

class SerializeNodeImpl implements SerializeNode
{
	private Source source;

	SerializeNodeImpl(Source source)
	{
		this.source = source;
	}

	@Override
	public void to(OutputStream out)
	{
		withSerializer(new SerializerConfigurerAdapter()).to(out);
	}

	@Override
	public void to(Writer out)
	{
		withSerializer(new SerializerConfigurerAdapter()).to(out);
	}

	@Override
	public byte[] toBytes()
	{
		return withSerializer(new SerializerConfigurerAdapter()).toBytes();
	}

	@Override
	public void to(File file)
	{
		withSerializer(new SerializerConfigurerAdapter()).to(file);
	}

	@Override
	public String toString()
	{
		return withSerializer(new SerializerConfigurerAdapter()).toString();
	}

	@Override
	public SerializeWithTransformerNode withSerializer(SerializerConfigurer serializerConfigurer)
	{
		return new SerializeWithTransformerNodeImpl(this.source, serializerConfigurer);
	}
}
