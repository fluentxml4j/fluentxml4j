package com.github.fluentxml4j;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class FluentXmlConfigurationExceptionTest
{
	private static final String MESSAGE = "aMessage";

	@Mock
	private Throwable cause;

	@Test
	public void causeOnly()
	{
		Exception ex = new FluentXmlConfigurationException(cause);

		assertThat(ex.getCause(), is(cause));
	}

	@Test
	public void causeAndMessage()
	{
		Exception ex = new FluentXmlConfigurationException(MESSAGE, cause);

		assertThat(ex.getCause(), is(cause));
		assertThat(ex.getMessage(), is(MESSAGE));
	}
}