package com.github.fluentxml4j.examples.query;

import com.github.fluentxml4j.junit.XmlSource;
import org.junit.Rule;
import org.junit.Test;
import org.w3c.dom.Element;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static com.github.fluentxml4j.FluentXml.from;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class QueryExample
{
	@Rule
	public XmlSource xmlSource = XmlSource.withData("<data><persons>" //
			+ "<person id='p1'><name>meiser</name><firstname>hans</firstname></person>" //
			+ "<person id='p2'><name>moeckl</name><firstname>joachim</firstname></person>" //
			+ "<person id='p3'><name>kerkeling</name><firstname>hans-peter</firstname></person>" //
			+ "</persons></data>");

	@Test
	public void countElements()
	{
		long personCount = from(xmlSource.asDocument())
				.selectElements("//person")
				.asStream()
				.count();

		assertThat(personCount, is(3L));
	}

	@Test
	public void selectElementsAndConvert()
	{
		Function<Element, String> fullNameFunction = (e) ->
				from(e).selectString("./firstname/text()").get()
						+ " "
						+ from(e).selectString("./name/text()").get();

		List<String> fullNames = from(xmlSource.asDocument())
				.selectElements("//person[contains(firstname/text(),'hans')]")
				.asStream()
				.map(fullNameFunction)
				.collect(toList());

		assertThat(fullNames, is(Arrays.asList("hans meiser", "hans-peter kerkeling")));
	}

	@Test
	public void countElementsShort()
	{
		int count = from(xmlSource.asDocument())
				.selectElements("//person")
				.count();

		assertThat(count, is(3));
	}


	@Test
	public void checkIfElementsExist()
	{
		boolean empty = from(xmlSource.asDocument())
				.selectElements("//person")
				.empty();

		assertThat(empty, is(false));
	}
}
