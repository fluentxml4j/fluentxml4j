package com.github.fluentxml4j.junit;

import java.io.InputStream;

class EmptyDataHolder implements DataHolder
{
	static final EmptyDataHolder INSTANCE = new EmptyDataHolder();

	@Override
	public InputStream getData()
	{
		throw new IllegalStateException("No data.");
	}
}
