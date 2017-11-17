package com.github.fluentxml4j.junit;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

class FileDataHolder implements DataHolder
{
	private File file;

	FileDataHolder(File file)
	{
		this.file = file;
	}

	@Override
	public InputStream getData()
	{
		try
		{
			return new FileInputStream(file);
		}
		catch (IOException ex)
		{
			throw new RuntimeException(ex);
		}
	}
}
