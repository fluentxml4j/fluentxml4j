package com.github.fluentxml4j.internal.query;

import com.github.fluentxml4j.junit.XmlSource;
import org.junit.ClassRule;
import org.junit.Test;
import org.w3c.dom.NodeList;

import static com.github.fluentxml4j.FluentXml.from;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SelectNodeListTest
{
	@ClassRule
	public static XmlSource docRule = XmlSource.withData("<a><b id=\"_1\">text1 word2</b><b id=\"_2\">text2 word3</b></a>");

	@Test
	public void selectElementsAsNodeList()
	{
		NodeList nodeList = from(docRule.asDocument())
				.selectElements("//b").asNodeList();
		assertThat(nodeList.getLength(), is(2));
	}
}
