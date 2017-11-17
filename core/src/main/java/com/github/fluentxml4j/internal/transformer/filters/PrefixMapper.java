package com.github.fluentxml4j.internal.transformer.filters;

import com.github.fluentxml4j.namespace.ImmutableNamespaceContext;

import java.util.HashMap;
import java.util.Map;

public class PrefixMapper
{
	private Map<String, String> prefixRepacements = new HashMap<>();

	public void addPrefixMapping(String prefix, String newPrefix)
	{
		this.prefixRepacements.put(prefix, newPrefix);
	}

	public ElementName mapElementName(String nsURI, String qName, ImmutableNamespaceContext namespaceContext)
	{
		ElementName elementName = ElementName.valueOf(qName);
		String prefix = elementName.prefix();
		String newPrefix = getNewPrefixFor(prefix);
		ElementName newElementName = elementName.withPrefix(newPrefix);

		String newNsURI = namespaceContext.getNamespaceURI(newPrefix);
		if (!nsURI.equals(newNsURI))
		{
			throw new IllegalStateException("Would change namespaceURI " + nsURI + " to " + newNsURI + " when changing prefix " + prefix + " to " + newPrefix + ".");
		}
		return newElementName;
	}

	private String getNewPrefixFor(String prefix)
	{
		for (Map.Entry<String, String> entry : this.prefixRepacements.entrySet())
		{
			if (entry.getKey().equals(prefix))
			{
				return entry.getValue();
			}
		}

		return prefix;
	}
}
