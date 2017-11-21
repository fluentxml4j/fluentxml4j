package com.github.fluentxml4j.examples.transform;

import com.github.fluentxml4j.junit.XmlSource;
import org.junit.Rule;
import org.junit.Test;

import static com.github.fluentxml4j.FluentXml.transform;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class JDOM2TransformExample
{
	@Rule
	public XmlSource xmlSource = XmlSource.withData("<ns1:input xmlns:ns1=\"uri:input\"/>");
	@Rule
	public XmlSource xsltSource = XmlSource.withData("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
			"<xsl:stylesheet version='1.0' xmlns:ns1=\"uri:input\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">" +
			"<xsl:output method='xml' indent='yes'/>" +
			"<xsl:template match='/ns1:input'><ns2:output xmlns:ns2=\"uri:output\"/></xsl:template>" +
			"</xsl:stylesheet>");

	@Test
	public void inputStreamWithStylesheetToJDOM2Document()
	{
		org.jdom2.Document doc = transform(xmlSource.asInputStream())
				.withStylesheet(xsltSource.asInputStream())
				.toJDOM2Document();

		assertThat(doc.getRootElement().getQualifiedName(), is("ns2:output"));
	}
}
