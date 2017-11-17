package com.github.fluentxml4j.internal.transform.filters;

import com.github.fluentxml4j.namespace.ImmutableNamespaceContext;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PrefixMapperTest
{
	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@InjectMocks
	private PrefixMapper prefixMapper;

	@Mock
	private ImmutableNamespaceContext namespaceContext;

	@Test
	public void mapsFromEmptyPrefix()
	{
		prefixMapper.addPrefixMapping("", "n1");
		when(this.namespaceContext.getNamespaceURI("")).thenReturn("nsURI");
		when(this.namespaceContext.getNamespaceURI("n1")).thenReturn("nsURI");

		ElementName mappedElementName = prefixMapper.mapElementName("nsURI", "element", this.namespaceContext);

		assertThat(mappedElementName.qName(), is("n1:element"));
		assertThat(mappedElementName.localName(), is("element"));
		assertThat(mappedElementName.prefix(), is("n1"));
	}

	@Test
	public void mapsToEmptyPrefix()
	{
		prefixMapper.addPrefixMapping("n1", "");
		when(this.namespaceContext.getNamespaceURI("")).thenReturn("nsURI");
		when(this.namespaceContext.getNamespaceURI("n1")).thenReturn("nsURI");

		ElementName mappedElementName = prefixMapper.mapElementName("nsURI", "n1:element", this.namespaceContext);

		assertThat(mappedElementName.qName(), is("element"));
		assertThat(mappedElementName.localName(), is("element"));
		assertThat(mappedElementName.prefix(), is(""));
	}

	@Test
	public void mapsPrefix()
	{
		prefixMapper.addPrefixMapping("n1", "n2");
		when(this.namespaceContext.getNamespaceURI("n1")).thenReturn("nsURI");
		when(this.namespaceContext.getNamespaceURI("n2")).thenReturn("nsURI");

		ElementName mappedElementName = prefixMapper.mapElementName("nsURI", "n1:element", this.namespaceContext);

		assertThat(mappedElementName.qName(), is("n2:element"));
		assertThat(mappedElementName.localName(), is("element"));
		assertThat(mappedElementName.prefix(), is("n2"));
	}

	@Test
	public void keepsPrefixWhenNotMapped()
	{
		prefixMapper.addPrefixMapping("n1", "n2");
		when(this.namespaceContext.getNamespaceURI("n1")).thenReturn("nsURI");
		when(this.namespaceContext.getNamespaceURI("n2")).thenReturn("nsURI");
		when(this.namespaceContext.getNamespaceURI("n3")).thenReturn("nsURI2");

		ElementName mappedElementName = prefixMapper.mapElementName("nsURI2", "n3:element", this.namespaceContext);

		assertThat(mappedElementName.qName(), is("n3:element"));
		assertThat(mappedElementName.localName(), is("element"));
		assertThat(mappedElementName.prefix(), is("n3"));
	}

	@Test
	public void failsWhenNamespaceURIWouldChange()
	{
		expectedException.expect(IllegalStateException.class);

		prefixMapper.addPrefixMapping("n1", "n2");
		when(this.namespaceContext.getNamespaceURI("n1")).thenReturn("nsURI");
		when(this.namespaceContext.getNamespaceURI("n2")).thenReturn("nsURI2");

		prefixMapper.mapElementName("nsURI", "n1:element", this.namespaceContext);
	}
}