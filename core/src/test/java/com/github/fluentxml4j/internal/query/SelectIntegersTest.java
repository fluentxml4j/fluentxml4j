package com.github.fluentxml4j.internal.query;

import com.github.fluentxml4j.junit.XmlSource;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.github.fluentxml4j.FluentXml.from;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SelectIntegersTest
{
	@ClassRule
	public static XmlSource docRule = XmlSource.withData("<data><value id=\"_1\" b=\"0\">1</value><value id=\"_2\" b=\"2\">3</value></data>");

	@Test
	public void selectAttributeIntegers()
	{
		List<Integer> values = from(docRule.asDocument())
				.selectIntegers("/data/value/@b").asList();
		assertThat(values, is(Arrays.asList(0, 2)));
	}

	@Test
	public void selectElementIntegers()
	{
		List<Integer> values = from(docRule.asDocument())
				.selectIntegers("/data/value/text()").asList();
		assertThat(values, is(Arrays.asList(1, 3)));
	}

	@Test
	public void selectAttributeInteger()
	{
		Integer value = from(docRule.asDocument())
				.selectInteger("/data/value[@id=\"_2\"]/@b").get();
		assertThat(value, is(2));
	}

	@Test
	public void selectElementInteger()
	{
		Integer value = from(docRule.asDocument())
				.selectInteger("/data/value[@id=\"_1\"]/text()").get();
		assertThat(value, is(1));
	}
}
