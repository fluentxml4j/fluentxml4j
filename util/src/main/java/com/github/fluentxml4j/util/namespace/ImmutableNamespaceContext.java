package com.github.fluentxml4j.util.namespace;

import javax.xml.namespace.NamespaceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ImmutableNamespaceContext implements NamespaceContext
{
	private Map<String, String> namespaceURIByPrefix = new HashMap<>();
	private Map<String, Set<String>> prefixesByNamespaceURI = new HashMap<>();

	public static ImmutableNamespaceContext empty()
	{
		return new ImmutableNamespaceContext();
	}

	public static ImmutableNamespaceContext ofMappings(String... mappings)
	{
		return ImmutableNamespaceContext.empty().withMappings(mappings);
	}

	public ImmutableNamespaceContext withMappings(String... mappings)
	{
		if (mappings.length == 0)
		{
			return this;
		}

		if (mappings.length % 2 != 0)
		{
			throw new IllegalArgumentException("Expected an even number of values, but was " + mappings.length + ".");
		}

		ImmutableNamespaceContext other = copy();
		int mappingsCount = mappings.length / 2;
		for (int i = 0; i < mappingsCount; ++i)
		{
			other.addMappingWithoutCopy(mappings[i * 2 + 0], mappings[i * 2 + 1]);
		}
		return other;
	}

	@Override
	public String getNamespaceURI(String prefix)
	{
		return this.namespaceURIByPrefix.get(prefix);
	}

	@Override
	public String getPrefix(String namespaceURI)
	{
		Iterator<String> prefixes = getPrefixes(namespaceURI);
		if (prefixes != null && prefixes.hasNext())
		{
			return prefixes.next();
		}

		return null;
	}

	@Override
	public Iterator<String> getPrefixes(String namespaceURI)
	{
		Set<String> prefixes = this.prefixesByNamespaceURI.get(namespaceURI);
		if (prefixes == null)
		{
			return Collections.<String>emptyList().iterator();
		}
		else
		{
			return Collections.unmodifiableSet(prefixes).iterator();
		}
	}

	public Set<String> getAllPrefixes()
	{
		return this.namespaceURIByPrefix.keySet();
	}

	public ImmutableNamespaceContext withMapping(String prefix, String namespaceURI)
	{
		return withMappings(prefix, namespaceURI);
	}

	public ImmutableNamespaceContext withoutPrefix(String prefix)
	{
		ImmutableNamespaceContext other = copy();
		other.removePrefixWithoutCopy(prefix);
		return other;
	}

	private ImmutableNamespaceContext copy()
	{
		ImmutableNamespaceContext other = new ImmutableNamespaceContext();
		other.namespaceURIByPrefix = new HashMap<>(this.namespaceURIByPrefix);
		other.prefixesByNamespaceURI = new HashMap<>();
		for (Map.Entry<String, Set<String>> entry : prefixesByNamespaceURI.entrySet())
		{
			other.prefixesByNamespaceURI.put(entry.getKey(), new HashSet<>(entry.getValue()));
		}
		return other;
	}

	private void addMappingWithoutCopy(String prefix, String namespaceURI)
	{
		removePrefixWithoutCopy(prefix);
		this.namespaceURIByPrefix.put(prefix, namespaceURI);
		Set<String> prefixes = this.prefixesByNamespaceURI.computeIfAbsent(namespaceURI,
				(s) -> new HashSet<>());
		prefixes.add(prefix);
	}

	private void removePrefixWithoutCopy(String prefix)
	{
		String mappedNamespaceURI = this.namespaceURIByPrefix.remove(prefix);
		if (mappedNamespaceURI != null)
		{
			this.prefixesByNamespaceURI.get(mappedNamespaceURI).remove(prefix);
		}
	}
}
