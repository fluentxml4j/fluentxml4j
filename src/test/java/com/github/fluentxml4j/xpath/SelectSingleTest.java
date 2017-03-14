package com.github.fluentxml4j.xpath;

import com.github.fluentxml4j.junit.DocumentTestRule;
import org.junit.ClassRule;
import org.junit.Test;
import org.w3c.dom.Element;

import static com.github.fluentxml4j.xpath.FluentXPath.from;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SelectSingleTest
{
	@ClassRule
	public static DocumentTestRule docRule = new DocumentTestRule("<a><b id=\"_1\">text1 word2</b><b id=\"_2\">text2 word3</b></a>");

	@Test
	public void selectSingleElementById()
	{
		Element element = from(docRule.document()).selectElement("//*[@id='_2']").get();
		assertThat(element.getAttribute("id"), is("_2"));
	}
}
