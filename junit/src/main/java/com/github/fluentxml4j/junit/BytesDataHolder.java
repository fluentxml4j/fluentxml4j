package com.github.fluentxml4j.junit;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

class BytesDataHolder implements DataHolder
{
	private byte[] data;

	BytesDataHolder(byte[] data)
	{
		this.data = data;
	}

	@Override
	public InputStream getData()
	{
		return new ByteArrayInputStream(this.data);
	}
}
