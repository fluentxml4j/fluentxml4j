package com.github.fluentxml4j.serializer;

import java.io.OutputStream;
import java.io.Writer;

public interface SerializeWithTransformerNode
{
	void to(OutputStream out);

	void to(Writer out);

	String toString();
}
