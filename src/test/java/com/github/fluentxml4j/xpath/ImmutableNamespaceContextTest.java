package com.github.fluentxml4j.xpath;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ImmutableNamespaceContextTest
{
	private ImmutableNamespaceContext origin = new ImmutableNamespaceContext();

	private ImmutableNamespaceContext derived;

	@Test
	public void empty() {
		assertThat(origin.getAllPrefixes(), is(Collections.emptySet()));
	}

	@Test
	public void add() {
		this.derived = this.origin.addMapping("prefix","nsURI");

		assertThat(origin.getAllPrefixes(), is(Collections.emptySet()));
		assertThat(derived.getAllPrefixes(), is(new HashSet<>(Arrays.asList("prefix"))));
	}
}