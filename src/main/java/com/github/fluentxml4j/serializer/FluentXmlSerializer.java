package com.github.fluentxml4j.serializer;

import org.w3c.dom.Document;

import javax.xml.transform.dom.DOMSource;
import java.io.OutputStream;
import java.io.Writer;

public class FluentXmlSerializer
{
	public SerializeNode serialize(Document doc)
	{
		return new SerializeNode()
		{
			@Override
			public void to(OutputStream out)
			{
				withSerializer(new SerializerConfigurerAdapter()).to(out);
			}

			@Override
			public void to(Writer out)
			{
				withSerializer(new SerializerConfigurerAdapter()).to(out);
			}

			@Override
			public String toString()
			{
				return withSerializer(new SerializerConfigurerAdapter()).toString();
			}

			@Override
			public SerializeWithTransformerNode withSerializer(SerializerConfigurer serializerConfigurer)
			{
				return new SerializeWithTransformerNodeImpl(new DOMSource(doc), serializerConfigurer);
			}
		};
	}

}
