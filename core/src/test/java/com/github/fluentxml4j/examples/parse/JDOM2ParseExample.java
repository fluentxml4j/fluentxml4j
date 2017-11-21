package com.github.fluentxml4j.examples.parse;

import com.github.fluentxml4j.junit.XmlSource;
import org.junit.Rule;
import org.junit.Test;

import static com.github.fluentxml4j.FluentXml.from;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class JDOM2ParseExample
{
	@Rule
	public XmlSource xmlSource = XmlSource.withData("<ns1:test xmlns:ns1=\"uri:ns1\"/>");

	@Test
	public void inputStreamToDocument()
	{
		org.jdom2.Document doc = from(xmlSource.asInputStream())
				.parse()
				.toJDOM2Document();

		assertThat(doc.getRootElement().getQualifiedName(), is("ns1:test"));
	}
}
