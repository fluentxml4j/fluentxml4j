package com.github.fluentxml4j.internal.util;

import com.github.fluentxml4j.FluentXmlProcessingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.util.JAXBSource;

public class JaxbUtils
{
	public static JAXBSource newJAXBSource(JAXBContext jaxbContext, Object object)
	{
		try
		{
			return new JAXBSource(jaxbContext, object);
		}
		catch (JAXBException ex)
		{
			throw new FluentXmlProcessingException(ex);
		}
	}
}
