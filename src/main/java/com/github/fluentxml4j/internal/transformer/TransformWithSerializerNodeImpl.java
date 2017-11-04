package com.github.fluentxml4j.internal.transformer;

import com.github.fluentxml4j.serializer.SerializeWithTransformerNode;

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
	public String toString()
	{
		return this.transformationChain.transformToString();
	}
}
