package com.github.fluentxml4j.serializer;

import javax.xml.transform.Transformer;

public interface SerializerConfigurer
{
	Transformer getSerializer();
}
