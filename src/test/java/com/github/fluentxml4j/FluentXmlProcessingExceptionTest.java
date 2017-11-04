package com.github.fluentxml4j;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class FluentXmlProcessingExceptionTest
{
	private static final String MESSAGE = "aMessage";

	@Mock
	private Throwable cause;

	@Test
	public void causeOnly()
	{
		Exception ex = new FluentXmlProcessingException(cause);

		assertThat(ex.getCause(), is(cause));
	}

	@Test
	public void messageOnly()
	{
		Exception ex = new FluentXmlProcessingException(MESSAGE);

		assertThat(ex.getMessage(), is(MESSAGE));
	}

	@Test
	public void causeAndMessage()
	{
		Exception ex = new FluentXmlProcessingException(MESSAGE, cause);

		assertThat(ex.getCause(), is(cause));
		assertThat(ex.getMessage(), is(MESSAGE));
	}
}