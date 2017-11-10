package com.github.fluentxml4j.serializer;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Result;
import java.io.OutputStream;
import java.io.Writer;

public interface SerializeWithTransformerNode
{
	void to(OutputStream out);

	void to(Writer out);

	String toString();

	void to(XMLEventWriter out);

	void to(XMLStreamWriter out);

	void to(Result out);
}
