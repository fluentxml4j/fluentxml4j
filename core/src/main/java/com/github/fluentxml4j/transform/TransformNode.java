package com.github.fluentxml4j.transform;

import com.github.fluentxml4j.serialize.SerializeWithTransformerNode;
import com.github.fluentxml4j.serialize.SerializerConfigurer;
import org.w3c.dom.Document;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.net.URL;

public interface TransformNode
{
	TransformNode withStylesheet(URL url);

	TransformNode withStylesheet(InputStream in);

	TransformNode withStylesheet(File file);

	TransformNode withStylesheet(Document doc);

	TransformNode withStylesheet(Source source);

	TransformNode withPrefixMapping(String prefix, String newPrefix);

	SerializeWithTransformerNode withSerializer(SerializerConfigurer serializerConfigurer);

	void to(Result result);

	void to(XMLEventWriter out);

	void to(XMLStreamWriter out);

	void to(OutputStream out);

	void to(Writer out);

	Document toDocument();

	String toString();
}
