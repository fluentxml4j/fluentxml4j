package com.github.fluentxml4j.serializer;

import org.w3c.dom.Document;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;

public class FluentXmlSerializer
{
	public static SerializeNode serialize(Document doc)
	{
		return new SerializeNode()
		{
			@Override
			public void to(OutputStream out)
			{
				serialize(doc, new StreamResult(out));
			}

			@Override
			public void to(Writer out)
			{
				serialize(doc, new StreamResult(out));
			}

			@Override
			public String toString()
			{
				StringWriter wr = new StringWriter();
				serialize(doc, new StreamResult(wr));
				return wr.getBuffer().toString();
			}

			private void serialize(Document doc, Result result)
			{
				try
				{
					TransformerFactory transformerFactory = TransformerFactory.newInstance();
					Transformer transformer = transformerFactory.newTransformer();
					transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
					transformer.setOutputProperty(OutputKeys.INDENT, "yes");
					transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
					transformer.transform(new DOMSource(doc), result);
				}
				catch (TransformerException ex)
				{
					throw new RuntimeException(ex);
				}
			}
		};
	}
}
