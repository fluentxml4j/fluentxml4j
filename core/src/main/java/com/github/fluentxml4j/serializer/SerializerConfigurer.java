package com.github.fluentxml4j.serializer;

import javax.xml.transform.sax.TransformerHandler;

public interface SerializerConfigurer
{
	TransformerHandler getSerializer();
}
