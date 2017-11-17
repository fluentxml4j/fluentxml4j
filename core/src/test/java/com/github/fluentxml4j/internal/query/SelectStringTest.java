package com.github.fluentxml4j.internal.query;

import com.github.fluentxml4j.junit.XmlSource;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.Optional;

import static com.github.fluentxml4j.FluentXml.from;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SelectStringTest
{
	@ClassRule
	public static XmlSource docRule = XmlSource.withData("<a><b id=\"_1\">text1 word2</b><b id=\"_2\">text2 word3</b></a>");

	@Test
	public void selectSingleString()
	{
		String text = from(docRule.asDocument()).selectString("//*[@id='_1']/text()").get();
		assertThat(text, is("text1 word2"));
	}

	@Test
	public void selectSingleElementByIdWhenElementNotExists()
	{
		Optional<String> text = from(docRule.asDocument()).selectString("//*[@id='doesNotExists']/text()");
		assertThat(text.isPresent(), is(false));
	}
}
