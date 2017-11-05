package com.github.fluentxml4j.internal.transformer;

import com.github.fluentxml4j.junit.XmlResource;
import com.github.fluentxml4j.serializer.SerializerConfigurerAdapter;
import org.junit.Rule;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FluentXmlTransformerIntegrationTest
{
	@Rule
	public XmlResource sourceDocumentRule = XmlResource.withData("<source/>");
	@Rule
	public XmlResource xsltDocumentRule = XmlResource.withData("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
			"<xsl:stylesheet version='1.0' xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">" +
			"<xsl:output method='xml' indent='yes'/>" +
			"<xsl:template match='/source'><transformed1/></xsl:template>" +
			"</xsl:stylesheet>");
	@Rule
	public XmlResource xsltDocumentRule2 = XmlResource.withData("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
			"<xsl:stylesheet version='1.0' xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">" +
			"<xsl:output method='xml' indent='yes'/>" +
			"<xsl:template match='/transformed1'><transformed2/></xsl:template>" +
			"</xsl:stylesheet>");
	@Rule
	public XmlResource xsltDocumentRule3 = XmlResource.withData("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
			"<xsl:stylesheet version='1.0' xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">" +
			"<xsl:output method='xml' indent='yes'/>" +
			"<xsl:template match='/transformed2'><transformed3/></xsl:template>" +
			"</xsl:stylesheet>");

	private FluentXmlTransformer fluentXmlTransformer = new FluentXmlTransformer();

	@Test
	public void documentToDocumentNoTransformer() throws Exception
	{
		Document resultDoc = fluentXmlTransformer
				.transform(sourceDocumentRule.asDocument())
				.toDocument();

		Element root = resultDoc.getDocumentElement();

		assertThat(root.getLocalName(), is("source"));
	}

	@Test
	public void documentToDocumentWithTransformers() throws Exception
	{
		Document resultDoc = fluentXmlTransformer
				.transform(sourceDocumentRule.asDocument())
				.withStylesheet(xsltDocumentRule.asDocument())
				.withStylesheet(xsltDocumentRule2.asInputStream())
				.withStylesheet(xsltDocumentRule3.asUrl())
				.toDocument();

		Element root = resultDoc.getDocumentElement();

		assertThat(root.getLocalName(), is("transformed3"));
	}

	@Test
	public void streamToDocumentWithTransformers() throws Exception
	{
		Document resultDoc = fluentXmlTransformer
				.transform(sourceDocumentRule.asInputStream())
				.withStylesheet(xsltDocumentRule.asDocument())
				.withStylesheet(xsltDocumentRule2.asInputStream())
				.withStylesheet(xsltDocumentRule3.asUrl())
				.toDocument();

		Element root = resultDoc.getDocumentElement();

		assertThat(root.getLocalName(), is("transformed3"));
	}

	@Test
	public void streamToStringWithTransformers() throws Exception
	{
		String resultXml = fluentXmlTransformer
				.transform(sourceDocumentRule.asInputStream())
				.withStylesheet(xsltDocumentRule.asDocument())
				.withStylesheet(xsltDocumentRule2.asInputStream())
				.withStylesheet(xsltDocumentRule3.asUrl())
				.withSerializer(new SerializerConfigurerAdapter()
				{
					@Override
					protected void configure(Transformer transformer)
					{
						super.configure(transformer);
						transformer.setOutputProperty(OutputKeys.INDENT, "no");
						transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
					}
				})
				.toString();

		assertThat(resultXml, is("<transformed3/>"));
	}
}