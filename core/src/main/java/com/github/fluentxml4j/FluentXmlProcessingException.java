package com.github.fluentxml4j;

public class FluentXmlProcessingException extends RuntimeException
{
	public FluentXmlProcessingException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public FluentXmlProcessingException(String message)
	{
		super(message);
	}

	public FluentXmlProcessingException(Throwable cause)
	{
		super(cause);
	}
}
