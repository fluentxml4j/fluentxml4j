package com.github.fluentxml4j.serializer;

import java.io.File;
import java.io.OutputStream;
import java.io.Writer;

public interface SerializeNode
{
	SerializeWithTransformerNode withSerializer(SerializerConfigurer serializerConfigurer);

	void to(OutputStream out);

	void to(Writer out);

	void to(File out);

	String toString();
}
