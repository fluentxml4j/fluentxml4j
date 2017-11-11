package com.github.fluentxml4j;

public class FluentXmlProcessingException extends RuntimeException
{
	private static final long serialVersionUID = 0xBADC0DEDL;

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
