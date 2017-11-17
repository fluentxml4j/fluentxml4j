package com.github.fluentxml4j.internal.serialize;

import com.github.fluentxml4j.internal.util.JaxbUtils;
import com.github.fluentxml4j.serialize.SerializeNode;
import org.w3c.dom.Document;

import javax.xml.bind.JAXBContext;
import javax.xml.transform.dom.DOMSource;

public class FluentXmlSerializer
{
	public SerializeNode serialize(Document doc)
	{
		return new SerializeNodeImpl(new DOMSource(doc));
	}

	public SerializeNode serialize(JAXBContext jaxbContext, Object object)
	{
		return new SerializeNodeImpl(JaxbUtils.newJAXBSource(jaxbContext, object));
	}

}
