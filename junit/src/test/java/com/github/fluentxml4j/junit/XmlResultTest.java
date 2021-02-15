package com.github.fluentxml4j.junit;

import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.charset.Charset;

import com.github.fluentxml4j.junit.XmlSource;
import com.github.fluentxml4j.junit.XmlResult;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class XmlResultTest
{
	private static final Charset UTF8 = Charset.forName("UTF-8");

	@Rule
	public XmlResult result = XmlResult.empty();

	@Test
	public void stringViaOutputStream() throws IOException
	{
		OutputStream out = this.result.getOutputStream();
		out.write("<test/>".getBytes(UTF8));
		out.close();

		assertThat(result.asString(), is("<test/>"));
	}

	@Test
	public void documentViaOutputStream() throws IOException
	{
		OutputStream out = this.result.getOutputStream();
		out.write("<test/>".getBytes(UTF8));
		out.close();

		assertThat(result.asDocument().getDocumentElement().getLocalName(), is("test"));
	}

	@Test
	public void stringViaWriter() throws IOException
	{
		Writer out = this.result.getWriter();
		out.write("<test/>");
		out.close();

		assertThat(result.asString(), is("<test/>"));
	}

	@Test
	public void documentViaWriter() throws IOException
	{
		Writer out = this.result.getWriter();
		out.write("<test/>");
		out.close();

		assertThat(result.asDocument().getDocumentElement().getLocalName(), is("test"));
	}
}
