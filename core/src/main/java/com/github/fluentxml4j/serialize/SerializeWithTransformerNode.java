package com.github.fluentxml4j.serialize;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Result;
import java.io.File;
import java.io.OutputStream;
import java.io.Writer;

public interface SerializeWithTransformerNode
{
	void to(OutputStream out);

	void to(Writer out);

	String toString();

	byte[] toBytes();

	void to(XMLEventWriter out);

	void to(XMLStreamWriter out);

	void to(Result out);

	void to(File file);
}
