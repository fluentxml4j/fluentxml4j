package com.github.fluentxml4j.serializer;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;

public class SerializerConfigurerAdapter implements SerializerConfigurer
{
	@Override
	public Transformer getSerializer()
	{
		try
		{
			TransformerFactory transformerFactory = buildTransformerFactory();
			configure(transformerFactory);
			Transformer transformer = buildTransformer(transformerFactory);
			configure(transformer);
			return transformer;
		}
		catch (TransformerConfigurationException ex)
		{
			throw new RuntimeException(ex);
		}
	}

	protected void configure(Transformer transformer)
	{
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
	}

	protected Transformer buildTransformer(TransformerFactory transformerFactory) throws TransformerConfigurationException
	{
		return transformerFactory.newTransformer();
	}

	protected void configure(TransformerFactory transformerFactory)
	{
	}

	protected TransformerFactory buildTransformerFactory()
	{
		return TransformerFactory.newInstance();
	}


}
