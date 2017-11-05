package com.github.fluentxml4j.internal.xpath;

import com.github.fluentxml4j.junit.XmlResource;
import org.junit.ClassRule;
import org.junit.Test;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.github.fluentxml4j.FluentXml.from;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SelectElementsTest
{
	@ClassRule
	public static XmlResource docRule = XmlResource.withData("<a><b id=\"_1\">text1 word2</b><b id=\"_2\">text2 word3</b></a>");

	@Test
	public void selectElementsAsListOfElements()
	{
		List<Element> elements = from(docRule.asDocument()).selectElements("//b").asList();
		assertThat(elements.size(), is(2));
		assertThat(elements.get(0).getAttribute("id"), is("_1"));
		assertThat(elements.get(1).getAttribute("id"), is("_2"));
	}

	@Test
	@Deprecated
	public void selectElementsForEachOldSyntax()
	{
		List<String> localNames = new ArrayList<>();
		for (Element element : from(docRule.asDocument())
				.selectElements("//*").iterate())
		{
			localNames.add(element.getLocalName());
		}

		assertThat(localNames, is(Arrays.asList("a", "b", "b")));
	}

	@Test
	public void selectElementsAndIterate()
	{
		List<String> localNames = new ArrayList<>();
		for (Element element : from(docRule.asDocument())
				.selectElements("//*"))
		{
			localNames.add(element.getLocalName());
		}

		assertThat(localNames, is(Arrays.asList("a", "b", "b")));
	}

	@Test
	public void selectElementsAndIterateViaForEach()
	{
		List<String> localNames = new ArrayList<>();
		from(docRule.asDocument()).selectElements("//*")
				.forEach((element) -> localNames.add(element.getLocalName()));

		assertThat(localNames, is(Arrays.asList("a", "b", "b")));
	}
}
