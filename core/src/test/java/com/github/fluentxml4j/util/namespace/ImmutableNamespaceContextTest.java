package com.github.fluentxml4j.util.namespace;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class ImmutableNamespaceContextTest
{
	private ImmutableNamespaceContext origin = new ImmutableNamespaceContext();

	private ImmutableNamespaceContext derived;

	@Test
	public void empty()
	{
		assertThat(origin.getAllPrefixes(), is(Collections.emptySet()));
	}

	@Test
	public void addMaping()
	{
		this.derived = this.origin.withMapping("prefix", "nsURI");

		assertThat(origin.getNamespaceURI("prefix"), is(nullValue()));
		assertThat(origin.getPrefix("nsURI"), is(nullValue()));
		assertThat(origin.getPrefixes("nsURI").hasNext(), is(false));
		assertThat(origin.getAllPrefixes(), is(Collections.emptySet()));

		assertThat(derived.getNamespaceURI("prefix"), is("nsURI"));
		assertThat(derived.getPrefix("nsURI"), is("prefix"));
		assertThat(derived.getPrefixes("nsURI").hasNext(), is(true));
		assertThat(derived.getAllPrefixes(), is(new HashSet<>(Arrays.asList("prefix"))));
	}

	@Test
	public void addMapingTwoPrefixes()
	{
		this.derived = this.origin.withMapping("prefix", "nsURI")
				.withMapping("prefix2", "nsURI");

		assertThat(origin.getNamespaceURI("prefix"), is(nullValue()));
		assertThat(origin.getNamespaceURI("prefix2"), is(nullValue()));
		assertThat(origin.getPrefix("nsURI"), is(nullValue()));
		assertThat(origin.getPrefixes("nsURI").hasNext(), is(false));
		assertThat(origin.getAllPrefixes(), is(Collections.emptySet()));

		assertThat(derived.getNamespaceURI("prefix"), is("nsURI"));
		assertThat(derived.getNamespaceURI("prefix2"), is("nsURI"));
		assertThat(derived.getPrefix("nsURI"), is(not(nullValue())));
		assertThat(derived.getPrefixes("nsURI").hasNext(), is(true));
		assertThat(derived.getAllPrefixes(), is(new HashSet<>(Arrays.asList("prefix", "prefix2"))));
	}

}
