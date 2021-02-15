package com.github.fluentxml4j.internal.query;

import com.github.fluentxml4j.junit.XmlSource;
import org.junit.ClassRule;
import org.junit.Test;
import org.w3c.dom.Attr;

import java.util.List;
import java.util.stream.Collectors;

import static com.github.fluentxml4j.FluentXml.from;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SelectAttrNodesTest
{
	@ClassRule
	public static XmlSource docRule = XmlSource.withData("<a><b id=\"_1\">text1 word2</b><b id=\"_2\">text2 word3</b></a>");

	@Test
	public void selectAttrNodes()
	{
		List<String> values = from(docRule.asDocument())
				.selectNodes("//b/@id")
				.asStream().map((node) -> ((Attr) node).getValue()).collect(Collectors.toList());
		assertThat(values.size(), is(2));
		assertThat(values.get(0), is("_1"));
		assertThat(values.get(1), is("_2"));
	}
}
