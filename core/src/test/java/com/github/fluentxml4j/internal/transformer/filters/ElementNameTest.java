package com.github.fluentxml4j.internal.transformer.filters;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

public class ElementNameTest
{
	@Test
	public void valueOfWithPrefix()
	{
		ElementName elementName = ElementName.valueOf("prefix:localName");

		assertThat(elementName.localName(), is("localName"));
		assertThat(elementName.qName(), is("prefix:localName"));
		assertThat(elementName.prefix(), is("prefix"));
	}

	@Test
	public void valueOfWithoutPrefix()
	{
		ElementName elementName = ElementName.valueOf("localName");

		assertThat(elementName.localName(), is("localName"));
		assertThat(elementName.qName(), is("localName"));
		assertThat(elementName.prefix(), is(""));
	}

	@Test
	public void valueOfWithoutPrefixWithPrefixAdded()
	{
		ElementName elementName = ElementName.valueOf("localName").withPrefix("prefix2");

		assertThat(elementName.localName(), is("localName"));
		assertThat(elementName.qName(), is("prefix2:localName"));
		assertThat(elementName.prefix(), is("prefix2"));
	}

	@Test
	public void equality()
	{
		assertThat(ElementName.valueOf("localName").withPrefix("prefix"), is(ElementName.valueOf("prefix:localName")));
		assertThat(ElementName.valueOf("localName").equals(ElementName.valueOf("prefix:localName")), is(false));
	}

	@Test
	public void hashing()
	{
		assertThat(ElementName.valueOf("prefix:localName").hashCode(), is(not(ElementName.valueOf("localName").hashCode())));
		assertThat(ElementName.valueOf("prefix:localName").hashCode(), is(ElementName.valueOf("prefix:localName").hashCode()));
	}

}