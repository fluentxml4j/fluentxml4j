package com.github.fluentxml4j.internal.transformer.filters;

public class ElementName
{
	private final String localName;
	private final String prefix;
	private final int hashCode;

	public static ElementName valueOf(String qName)
	{
		String prefix = "";
		String localName = qName;
		int colonPos = qName.indexOf(":");
		if (colonPos != -1)
		{
			localName = qName.substring(colonPos + 1);
			prefix = qName.substring(0, colonPos);
		}

		return new ElementName(localName, prefix);
	}

	public ElementName(String localName, String prefix)
	{
		this.localName = localName;
		this.prefix = prefix;
		this.hashCode = qName().hashCode();
	}

	public ElementName withPrefix(String prefix)
	{
		return new ElementName(localName, prefix);
	}

	public String localName()
	{
		return this.localName;
	}

	public String qName()
	{
		if (this.prefix.isEmpty())
		{
			return this.localName;
		}
		else
		{
			return this.prefix + ":" + this.localName;
		}
	}

	public String prefix()
	{
		return this.prefix;
	}

	@Override
	public int hashCode()
	{
		return this.hashCode;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ElementName that = (ElementName) o;

		if (!localName.equals(that.localName)) return false;
		return prefix.equals(that.prefix);
	}

	@Override
	public String toString()
	{
		return qName();
	}
}
