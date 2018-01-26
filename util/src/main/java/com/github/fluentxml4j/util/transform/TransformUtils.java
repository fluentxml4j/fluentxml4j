package com.github.fluentxml4j.util.transform;

import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;

/**
 * Utilities related to javax.xml.transform.
 */
public class TransformUtils
{
	private TransformUtils()
	{
	}

	public static SAXTransformerFactory newSAXTransformerFactory()
	{
		return (SAXTransformerFactory) SAXTransformerFactory.newInstance();
	}

	/**
	 * @throws IllegalStateException when a {@link TransformerConfigurationException} is catched.
	 * @return The transformer handler.
	 */
	public static TransformerHandler newTransformerHandler()
	{
		try
		{
			return newSAXTransformerFactory().newTransformerHandler();
		}
		catch (TransformerConfigurationException ex)
		{
			throw new IllegalStateException(ex);
		}
	}

	/**
	 * @throws IllegalStateException when a {@link TransformerConfigurationException} is catched.
	 * @param templates Templates to create the transformer with.
	 * @return The transformer handler.
	 */
	public static TransformerHandler newTransformerHandler(Templates templates)
	{
		try
		{
			return newSAXTransformerFactory().newTransformerHandler(templates);
		}
		catch (TransformerConfigurationException ex)
		{
			throw new IllegalStateException(ex);
		}
	}

	/**
	 * @throws IllegalStateException when a {@link TransformerConfigurationException} is catched.
	 * @param source The transformer source.
	 * @return The transformer handler.
	 */
	public static TransformerHandler newTransformerHandler(Source source)
	{
		try
		{
			return newSAXTransformerFactory().newTransformerHandler(source);
		}
		catch (TransformerConfigurationException ex)
		{
			throw new IllegalStateException(ex);
		}
	}
}
