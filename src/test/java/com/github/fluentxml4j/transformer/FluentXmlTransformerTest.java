package com.github.fluentxml4j.transformer;

import com.github.fluentxml4j.DocumentTestRule;
import org.junit.Rule;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FluentXmlTransformerTest
{
	@Rule
	public DocumentTestRule sourceDocumentRule = new DocumentTestRule("<source/>");
	@Rule
	public DocumentTestRule xsltDocumentRule = new DocumentTestRule("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
			"<xsl:stylesheet version='1.0' xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">" +
			"<xsl:output method='xml' indent='yes'/>" +
			"<xsl:template match='/source'><transformed1/></xsl:template>" +
			"</xsl:stylesheet>");
	@Rule
	public DocumentTestRule xsltDocumentRule2 = new DocumentTestRule("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
			"<xsl:stylesheet version='1.0' xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">" +
			"<xsl:output method='xml' indent='yes'/>" +
			"<xsl:template match='/transformed1'><transformed2/></xsl:template>" +
			"</xsl:stylesheet>");
	@Rule
	public DocumentTestRule xsltDocumentRule3 = new DocumentTestRule("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
			"<xsl:stylesheet version='1.0' xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">" +
			"<xsl:output method='xml' indent='yes'/>" +
			"<xsl:template match='/transformed2'><transformed3/></xsl:template>" +
			"</xsl:stylesheet>");

	private FluentXmlTransformer fluentXmlTransformer = new FluentXmlTransformer();

	@Test
	public void documentToDocumentNoTransformer() throws Exception
	{
		Document resultDoc = fluentXmlTransformer
				.transform(sourceDocumentRule.document())
				.toDocument();

		Element root = resultDoc.getDocumentElement();

		assertThat(root.getLocalName(), is("source"));
	}

	@Test
	public void documentToDocument() throws Exception
	{
		Document resultDoc = fluentXmlTransformer
				.transform(sourceDocumentRule.document())
				.withStylesheet(xsltDocumentRule.document())
				.withStylesheet(xsltDocumentRule2.inputStream())
				.withStylesheet(xsltDocumentRule3.url())
				.toDocument();

		Element root = resultDoc.getDocumentElement();

		assertThat(root.getLocalName(), is("transformed3"));
	}

	@Test
	public void streamToDocument() throws Exception
	{
		Document resultDoc = fluentXmlTransformer
				.transform(sourceDocumentRule.inputStream())
				.withStylesheet(xsltDocumentRule.document())
				.withStylesheet(xsltDocumentRule2.inputStream())
				.withStylesheet(xsltDocumentRule3.url())
				.toDocument();

		Element root = resultDoc.getDocumentElement();

		assertThat(root.getLocalName(), is("transformed3"));
	}
}