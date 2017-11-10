package com.github.fluentxml4j.internal.serializer;

import com.github.fluentxml4j.serializer.SerializeNode;
import org.w3c.dom.Document;

import javax.xml.transform.dom.DOMSource;

public class FluentXmlSerializer
{
	public SerializeNode serialize(Document doc)
	{
		return new SerializeNodeImpl(new DOMSource(doc));
	}

}
