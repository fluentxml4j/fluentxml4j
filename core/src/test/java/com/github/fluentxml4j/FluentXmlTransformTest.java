package com.github.fluentxml4j;

import com.github.fluentxml4j.internal.transformer.FluentXmlTransformer;
import com.github.fluentxml4j.internal.xpath.FluentXPath;
import com.github.fluentxml4j.transformer.TransformNode;
import com.github.fluentxml4j.xpath.FromNode;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.w3c.dom.Document;

import java.io.InputStream;

import static com.github.fluentxml4j.FluentXml.from;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FluentXmlTransformTest
{
	@Rule
	public FluentXmlInjectionRule fluentXmlInjectionRule = new FluentXmlInjectionRule();

	@Mock
	private FluentXmlTransformer fluentXmlTransformer;

	@Mock
	private Document document;

	@Mock
	private TransformNode transformNode;

	@Mock
	private InputStream inputStream;

	@Before
	public void setUp()
	{
		fluentXmlInjectionRule.setFluentXmlTransformer(this.fluentXmlTransformer);
	}

	@Test
	public void transformDocument()
	{
		when(fluentXmlTransformer.transform(document)).thenReturn(transformNode);

		TransformNode transformNodeReturned = FluentXml.transform(document);

		assertThat(transformNodeReturned, is(this.transformNode));
	}


	@Test
	public void transformStream()
	{
		when(fluentXmlTransformer.transform(inputStream)).thenReturn(transformNode);

		TransformNode transformNodeReturned = FluentXml.transform(inputStream);

		assertThat(transformNodeReturned, is(this.transformNode));
	}
}