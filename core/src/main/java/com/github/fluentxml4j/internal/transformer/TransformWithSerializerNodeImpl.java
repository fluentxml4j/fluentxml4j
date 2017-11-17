package com.github.fluentxml4j.internal.transformer;

import com.github.fluentxml4j.serializer.SerializeWithTransformerNode;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Result;
import java.io.File;
import java.io.OutputStream;
import java.io.Writer;

class TransformWithSerializerNodeImpl implements SerializeWithTransformerNode
{
	private TransformationChain transformationChain;

	TransformWithSerializerNodeImpl(TransformationChain transformationChain)
	{
		this.transformationChain = transformationChain;
	}

	@Override
	public void to(OutputStream out)
	{
		this.transformationChain.transformTo(out);
	}

	@Override
	public void to(Writer out)
	{
		this.transformationChain.transformTo(out);
	}

	@Override
	public void to(XMLStreamWriter out)
	{
		this.transformationChain.transformTo(out);
	}

	@Override
	public void to(XMLEventWriter out)
	{
		this.transformationChain.transformTo(out);
	}

	@Override
	public void to(File file)
	{
		this.transformationChain.transformTo(file);
	}

	@Override
	public void to(Result out)
	{
		this.transformationChain.transformTo(out);
	}

	@Override
	public String toString()
	{
		return this.transformationChain.transformToString();
	}
}
