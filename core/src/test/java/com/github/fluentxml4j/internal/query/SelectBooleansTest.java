package com.github.fluentxml4j.internal.query;

import com.github.fluentxml4j.junit.XmlSource;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.github.fluentxml4j.FluentXml.from;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SelectBooleansTest
{
	@ClassRule
	public static XmlSource docRule = XmlSource.withData("<data><value id=\"_1\" b=\"false\">true</value><value id=\"_2\" b=\"true\">false</value></data>");

	@Test
	public void selectAttributeBooleans()
	{
		List<Boolean> values = from(docRule.asDocument())
				.selectBooleans("/data/value/@b").asList();
		assertThat(values, is(Arrays.asList(Boolean.FALSE, Boolean.TRUE)));
	}

	@Test
	public void selectElementBooleans()
	{
		List<Boolean> values = from(docRule.asDocument())
				.selectBooleans("/data/value/text()").asList();
		assertThat(values, is(Arrays.asList(Boolean.TRUE, Boolean.FALSE)));
	}

	@Test
	public void selectAttributeBoolean()
	{
		Boolean value = from(docRule.asDocument())
				.selectBoolean("/data/value[@id=\"_2\"]/@b").get();
		assertThat(value, is(Boolean.TRUE));
	}

	@Test
	public void selectElementBoolean()
	{
		Boolean value = from(docRule.asDocument())
				.selectBoolean("/data/value[@id=\"_1\"]/text()").get();
		assertThat(value, is(Boolean.TRUE));
	}
}
