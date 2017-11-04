package com.github.fluentxml4j.internal.xpath;

import com.github.fluentxml4j.FluentXmlProcessingException;
import com.github.fluentxml4j.xpath.ImmutableNamespaceContext;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FromNodeImplTest
{
	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@InjectMocks
	private FromNodeImpl fromNode;
	@Mock
	private Node baseNode;
	@Mock
	private FluentXPathContext fluentXPathContext;
	@Mock
	private XPathExpression xPathExpression;
	@Mock
	private NodeList nodeList;

	@Before
	public void before() throws XPathExpressionException
	{
		when(this.fluentXPathContext.compile(eq("aQuery"), any(ImmutableNamespaceContext.class))).thenReturn(this.xPathExpression);
		when(this.xPathExpression.evaluate(this.baseNode, XPathConstants.NODESET)).thenReturn(nodeList);
	}

	@Test
	public void selectStringGivesEmptyOptionalWhenNoResultFound() throws XPathExpressionException
	{
		givenResultNodeSetIsEmpty();

		boolean present = this.fromNode.selectString("aQuery").isPresent();

		assertThat(present, is(false));
	}

	@Test
	public void singleStringWhenSingleNodeFound() throws XPathExpressionException
	{
		givenResultNodeSetContains("string1");

		String result = this.fromNode.selectString("aQuery").get();

		assertThat(result, is("string1"));
	}

	@Test
	public void selectStringFailsWhenMultipleNodesFound() throws XPathExpressionException
	{
		expectedException.expect(FluentXmlProcessingException.class);

		givenResultNodeSetContains("string1", "string2");

		this.fromNode.selectString("aQuery");
	}

	@Test
	public void selectStringsGivesListOfStrings() throws XPathExpressionException
	{
		givenResultNodeSetContains("string1", "string2");

		List<String> result = this.fromNode.selectStrings("aQuery").asList();

		assertThat(result, is(Arrays.asList("string1", "string2")));
	}

	private void givenResultNodeSetContains(String... values)
	{
		when(this.nodeList.getLength()).thenReturn(values.length);
		for (int i = 0; i < values.length; ++i)
		{
			Text textNode = mock(Text.class);
			when(textNode.getData()).thenReturn(values[i]);
			when(this.nodeList.item(i)).thenReturn(textNode);
		}
	}

	private void givenResultNodeSetIsEmpty()
	{
		when(this.nodeList.getLength()).thenReturn(0);
	}
}