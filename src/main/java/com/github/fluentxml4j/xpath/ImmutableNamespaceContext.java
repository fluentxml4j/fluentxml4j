package com.github.fluentxml4j.xpath;

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

	public Set<String> getAllPrefixes()
	{
		return this.namespaceURIByPrefix.keySet();
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

	public ImmutableNamespaceContext addMapping(String prefix, String namespaceURI)
	{
		ImmutableNamespaceContext other = copy();
		other.addMappingWithoutCopy(prefix, namespaceURI);
		return other;
	}

	private void addMappingWithoutCopy(String prefix, String namespaceURI)
	{
		removePrefixWithoutCopy(prefix);
		this.namespaceURIByPrefix.put(prefix, namespaceURI);
		Set<String> prefixesByNamespaceURI = this.prefixesByNamespaceURI.get(namespaceURI);
		if (prefixesByNamespaceURI == null)
		{
			prefixesByNamespaceURI = new HashSet<>();
			this.prefixesByNamespaceURI.put(namespaceURI, prefixesByNamespaceURI);
		}
		prefixesByNamespaceURI.add(prefix);
	}

	public ImmutableNamespaceContext removePrefix(String prefix)
	{
		ImmutableNamespaceContext other = copy();
		other.removePrefixWithoutCopy(prefix);
		return other;
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
