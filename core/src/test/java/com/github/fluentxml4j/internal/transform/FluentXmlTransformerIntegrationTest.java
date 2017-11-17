package com.github.fluentxml4j.internal.transform;

import com.github.fluentxml4j.junit.AutoFlushPolicy;
import com.github.fluentxml4j.junit.XmlResult;
import com.github.fluentxml4j.junit.XmlSource;
import com.github.fluentxml4j.serialize.SerializerConfigurerAdapter;
import org.junit.Rule;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.bind.JAXBContext;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.stream.StreamResult;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FluentXmlTransformerIntegrationTest
{
	@Rule
	public XmlSource sourceDocumentRule = XmlSource.withData("<source/>");
	@Rule
	public XmlResult resultDocument = XmlResult.empty();
	@Rule
	public XmlSource sourceDocumentWithNSRule = XmlSource.withData("<ns1:source xmlns=\"uri:ns\" xmlns:ns1=\"uri:ns\" xmlns:ns2=\"uri:ns\"></ns1:source>");
	@Rule
	public XmlSource sourceDocumentWithDefaultNSRule = XmlSource.withData("<source xmlns=\"uri:ns\" xmlns:ns1=\"uri:ns\"></source>");

	@Rule
	public XmlSource xsltDocumentRule = XmlSource.withData("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
			"<xsl:stylesheet version='1.0' xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">" +
			"<xsl:output method='xml' indent='yes'/>" +
			"<xsl:template match='/source'><transformed1/></xsl:template>" +
			"</xsl:stylesheet>");
	@Rule
	public XmlSource xsltDocumentRule2 = XmlSource.withData("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
			"<xsl:stylesheet version='1.0' xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">" +
			"<xsl:output method='xml' indent='yes'/>" +
			"<xsl:template match='/transformed1'><transformed2/></xsl:template>" +
			"</xsl:stylesheet>");
	@Rule
	public XmlSource xsltDocumentRule3 = XmlSource.withData("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
			"<xsl:stylesheet version='1.0' xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">" +
			"<xsl:output method='xml' indent='yes'/>" +
			"<xsl:template match='/transformed2'><transformed3/></xsl:template>" +
			"</xsl:stylesheet>");
	@Rule
	public XmlSource xsltDocumentRule4 = XmlSource.withData("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
			"<xsl:stylesheet version='1.0' xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">" +
			"<xsl:output method='xml' indent='yes'/>" +
			"<xsl:template match='/transformed3'><transformed4/></xsl:template>" +
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
	public void documentToStreamNoTransformer() throws Exception
	{
		fluentXmlTransformer
				.transform(sourceDocumentRule.asDocument())
				.to(this.resultDocument.getOutputStream());

		assertThat(this.resultDocument.asString(), containsString("<source/>"));
	}

	@Test
	public void documentToWriterNoTransformer() throws Exception
	{
		fluentXmlTransformer
				.transform(sourceDocumentRule.asDocument())
				.to(this.resultDocument.getWriter());

		assertThat(this.resultDocument.asString(), containsString("<source/>"));
	}

	@Test
	public void documentToResultNoTransformer() throws Exception
	{
		fluentXmlTransformer
				.transform(sourceDocumentRule.asDocument())
				.to(new StreamResult(this.resultDocument.getWriter()));

		assertThat(this.resultDocument.asString(), containsString("<source/>"));
	}

	@Test
	public void documentToXMLEventWriterNoTransformerWithAutoFlushEnabled() throws Exception
	{
		fluentXmlTransformer
				.transform(sourceDocumentRule.asDocument())
				.to(this.resultDocument.getXMLEventWriter(AutoFlushPolicy.AUTO_FLUSH));

		assertThat(this.resultDocument.asString(), containsString("<source></source>"));
	}

	@Test
	public void documentToXMLStreamWriterNoTransformerWithAutoFlushEnabled() throws Exception
	{
		fluentXmlTransformer
				.transform(sourceDocumentRule.asDocument())
				.to(this.resultDocument.getXMLStreamWriter(AutoFlushPolicy.AUTO_FLUSH));

		assertThat(this.resultDocument.asString(), containsString("<source></source>"));
	}

	@Test
	public void documentToDocumentWithTransformers() throws Exception
	{
		Document resultDoc = fluentXmlTransformer
				.transform(sourceDocumentRule.asDocument())
				.withStylesheet(xsltDocumentRule.asDocument())
				.withStylesheet(xsltDocumentRule2.asInputStream())
				.withStylesheet(xsltDocumentRule3.asUrl())
				.withStylesheet(xsltDocumentRule4.asFile())
				.toDocument();

		Element root = resultDoc.getDocumentElement();

		assertThat(root.getLocalName(), is("transformed4"));
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

	@Test
	public void xmlStreamReaderToXMLEventWriterNoTransformerWithAutoFlushEnabled() throws Exception
	{
		fluentXmlTransformer
				.transform(sourceDocumentRule.asXMLStreamReader())
				.to(this.resultDocument.getXMLEventWriter(AutoFlushPolicy.AUTO_FLUSH));

		assertThat(this.resultDocument.asString(), containsString("<source></source>"));
	}

	@Test
	public void xmlEventReaderXMLStreamWriterNoTransformerWithAutoFlushEnabled() throws Exception
	{
		fluentXmlTransformer
				.transform(sourceDocumentRule.asXMLEventReader())
				.to(this.resultDocument.getXMLStreamWriter(AutoFlushPolicy.AUTO_FLUSH));

		assertThat(this.resultDocument.asString(), containsString("<source></source>"));
	}

	@Test
	public void documentWithPrefixMappingToString() throws Exception
	{
		String xml = fluentXmlTransformer
				.transform(sourceDocumentWithNSRule.asDocument())
				.withPrefixMapping("ns1", "ns2")
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

		assertThat(xml, is("<ns2:source xmlns:ns2=\"uri:ns\" xmlns=\"uri:ns\" xmlns:ns1=\"uri:ns\"/>"));
	}

	@Test
	public void documentWithPrefixMappingToDefaultToString() throws Exception
	{
		String xml = fluentXmlTransformer
				.transform(sourceDocumentWithNSRule.asDocument())
				.withPrefixMapping("ns1", "")
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

		assertThat(xml, is("<source xmlns=\"uri:ns\" xmlns:ns1=\"uri:ns\" xmlns:ns2=\"uri:ns\"/>"));
	}

	@Test
	public void documentWithPrefixMappingFromDefaultToString() throws Exception
	{
		String xml = fluentXmlTransformer
				.transform(sourceDocumentWithDefaultNSRule.asDocument())
				.withPrefixMapping("", "ns1")
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

		assertThat(xml, is("<ns1:source xmlns:ns1=\"uri:ns\" xmlns=\"uri:ns\"/>"));
	}

	@Test
	public void transformWithJAXBAndStylesheetToString() throws Exception
	{
		String xml = fluentXmlTransformer.transform(JAXBContext.newInstance(JaxbSource.class), new JaxbSource())
				.withStylesheet(this.xsltDocumentRule.asDocument())
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

		assertThat(xml, is("<transformed1/>"));
	}
}