package com.github.fluentxml4j.internal.transformer.filters;

import org.xml.sax.Locator;

import java.util.ArrayList;
import java.util.List;

public class XmlLocation
{
	private List<Entry> entries = new ArrayList<>();

	private Locator locator;

	private interface Entry
	{
		void appendTo(StringBuilder buf);
	}

	private static class ElementEntry implements Entry
	{
		private String name;
		private int childCount;
		private int index;

		private ElementEntry(String name, int index)
		{
			this.name = name;
			this.index = index;
			this.childCount = 0;
		}

		@Override
		public void appendTo(StringBuilder buf)
		{
			buf.append(this.name).append("[").append(this.index).append("]");
		}
	}

	public static XmlLocation root()
	{
		return new XmlLocation();
	}

	private XmlLocation()
	{
	}

	public void removeEntry()
	{
		this.entries.remove(this.entries.size() - 1);
	}

	public void addElementEntry(String name)
	{
		if (this.entries.isEmpty())
		{
			this.entries.add(new ElementEntry(name, 0));
		}
		else
		{
			ElementEntry parent = (ElementEntry) this.entries.get(this.entries.size() - 1);
			this.entries.add(new ElementEntry(name, parent.childCount++));
		}
	}

	public String getLocation()
	{
		StringBuilder buf = new StringBuilder();

		if (this.locator != null)
		{
			buf.append(String.format("%s[%d:%d]:", this.locator.getSystemId(),
					this.locator.getLineNumber(), this.locator.getColumnNumber()));
		}
		else
		{
			buf.append("?");
		}

		for (Entry entry : this.entries)
		{
			buf.append("/");
			entry.appendTo(buf);
		}

		if (buf.length() == 0)
		{
			buf.append("/");
		}

		return buf.toString();
	}
}
