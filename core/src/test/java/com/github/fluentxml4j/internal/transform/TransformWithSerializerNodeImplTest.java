package com.github.fluentxml4j.internal.transform;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.OutputStream;
import java.io.Writer;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TransformWithSerializerNodeImplTest
{
	@InjectMocks
	private TransformWithSerializerNodeImpl node;

	@Mock
	private TransformationChain transformationChain;

	@Mock
	private Writer writer;

	@Mock
	private OutputStream outputStream;

	@Test
	public void toWriter()
	{
		node.to(writer);

		verify(transformationChain).transformTo(writer);
	}

	@Test
	public void toOutputStream()
	{
		node.to(outputStream);

		verify(transformationChain).transformTo(outputStream);
	}

	@Test
	public void transformToString()
	{
		node.toString();

		verify(transformationChain).transformToString();
	}
}