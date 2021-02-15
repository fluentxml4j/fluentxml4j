package com.github.fluentxml4j;

import com.github.fluentxml4j.internal.query.FluentQuery;
import com.github.fluentxml4j.query.QueryFromNode;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.w3c.dom.Document;

import static com.github.fluentxml4j.FluentXml.from;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FluentXmlQueryTest
{
	@Rule
	public FluentXmlInjectionRule fluentXmlInjectionRule = new FluentXmlInjectionRule();

	@Mock
	private FluentQuery fluentQuery;

	@Mock
	private Document document;

	@Mock
	private QueryFromNode fromNode;

	@Before
	public void setUp()
	{
		fluentXmlInjectionRule.setFluentXPath(this.fluentQuery);
		when(fluentQuery.from(document)).thenReturn(fromNode);
	}

	@Test
	public void serializeDocument()
	{
		QueryFromNode fromNodeReturned = from(document);

		assertThat(fromNodeReturned, is(this.fromNode));
	}
}
