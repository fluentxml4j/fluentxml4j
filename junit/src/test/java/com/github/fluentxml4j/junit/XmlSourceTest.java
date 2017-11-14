package com.github.fluentxml4j.junit;

import org.apache.commons.io.IOUtils;
import org.junit.Rule;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class XmlSourceTest
{
	@Rule
	public XmlSource xml = XmlSource.withData("<test/>");

	@Test
	public void asBytes() throws UnsupportedEncodingException
	{
		assertThat(xml.asBytes("UTF-8"), is("<test/>".getBytes("UTF-8")));
	}

	@Test
	public void asString()
	{
		assertThat(xml.asString(), is("<test/>"));
	}

	@Test
	public void asDocument()
	{
		assertThat(xml.asDocument().getDocumentElement().getLocalName(), is("test"));
	}

	@Test
	public void asFile() throws IOException
	{
		String xmlRead = IOUtils.toString(new FileInputStream(xml.asFile()), "UTF-8");

		assertThat(xmlRead, is("<test/>"));
	}

	@Test
	public void asURL() throws IOException
	{
		String xmlRead = IOUtils.toString(xml.asUrl(), "UTF-8");

		assertThat(xmlRead, is("<test/>"));
	}

	@Test
	public void asReader() throws IOException
	{
		String xmlRead = IOUtils.toString(xml.asReader());

		assertThat(xmlRead, is("<test/>"));
	}

	@Test
	public void asStream() throws IOException
	{
		String xmlRead = IOUtils.toString(xml.asInputStream(), "UTF-8");

		assertThat(xmlRead, is("<test/>"));
	}
}