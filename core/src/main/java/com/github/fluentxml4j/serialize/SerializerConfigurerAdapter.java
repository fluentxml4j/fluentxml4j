package com.github.fluentxml4j.serialize;

import com.github.fluentxml4j.FluentXmlConfigurationException;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;

public class SerializerConfigurerAdapter implements SerializerConfigurer
{
	@Override
	public TransformerHandler getSerializer()
	{
		try
		{
			SAXTransformerFactory transformerFactory = buildTransformerFactory();
			configure(transformerFactory);
			TransformerHandler transformer = buildTransformer(transformerFactory);
			configure(transformer.getTransformer());
			return transformer;
		}
		catch (TransformerConfigurationException ex)
		{
			throw new FluentXmlConfigurationException(ex);
		}
	}

	protected void configure(Transformer transformer)
	{
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
	}

	protected TransformerHandler buildTransformer(SAXTransformerFactory transformerFactory) throws TransformerConfigurationException
	{
		return transformerFactory.newTransformerHandler();
	}

	protected void configure(SAXTransformerFactory transformerFactory)
	{
		// override for customization
	}

	protected SAXTransformerFactory buildTransformerFactory()
	{
		return (SAXTransformerFactory) SAXTransformerFactory.newInstance();
	}


}
