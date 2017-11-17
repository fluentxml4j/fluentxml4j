package com.github.fluentxml4j.serialize;

import javax.xml.transform.sax.TransformerHandler;

public interface SerializerConfigurer
{
	TransformerHandler getSerializer();
}
