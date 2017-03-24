package com.github.fluentxml4j.xpath;

import com.github.fluentxml4j.junit.DocumentTestRule;
import org.junit.ClassRule;
import org.junit.Test;
import org.w3c.dom.Attr;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.github.fluentxml4j.xpath.FluentXPath.from;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SelectStringsTest
{
	@ClassRule
	public static DocumentTestRule docRule = new DocumentTestRule("<a><b id=\"_1\">text1 word2</b><b id=\"_2\">text2 word3</b></a>");

	@Test
	public void selectAttributeStrings()
	{
		List<String> values = from(docRule.document())
				.selectStrings("//b/@id").asList();
		assertThat(values.size(), is(2));
		assertThat(values.get(0), is("_1"));
		assertThat(values.get(1), is("_2"));
	}

	@Test
	public void selectWords()
	{
		Set<String> words = from(docRule.document())
				.selectStrings("//text()")
				.asStream().flatMap((s) ->
						Arrays.stream(s.split("[\\s]+")))
				.collect(Collectors.toSet());
		assertThat(words, hasItems("text1", "text2", "word2", "word3"));
	}
}
