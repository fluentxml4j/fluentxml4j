package com.github.fluentxml4j;

public class FluentXmlConfigurationException extends RuntimeException
{
	private static final long serialVersionUID = 0xABAD1DEAL;

	public FluentXmlConfigurationException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public FluentXmlConfigurationException(Throwable cause)
	{
		super(cause);
	}
}
