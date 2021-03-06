package com.github.fluentxml4j.internal.query;

import com.github.fluentxml4j.util.namespace.ImmutableNamespaceContext;
import com.github.fluentxml4j.query.XPathConfigurer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FluentXPathContextTest
{
	@InjectMocks
	private FluentXPathContext fluentXPathContext;
	@Mock
	private XPathConfigurer xPathConfigurer;
	@Mock
	private XPath xpath;
	@Mock
	private ImmutableNamespaceContext immutableNamespaceContext;
	@Mock
	private XPathExpression xpathExpr;

	@Test
	public void compile() throws XPathExpressionException
	{
		when(this.xPathConfigurer.getXPath(this.immutableNamespaceContext)).thenReturn(this.xpath);
		when(this.xpath.compile("aQuery")).thenReturn(this.xpathExpr);

		XPathExpression xpathExprReturned = this.fluentXPathContext.compile("aQuery", this.immutableNamespaceContext);

		assertThat(xpathExprReturned, is(xpathExpr));
	}
}
