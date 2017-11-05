package com.github.fluentxml4j;

import java.util.function.Supplier;

class ThreadLocalInstanceSupplier<T> implements Supplier<T>
{
	private ThreadLocal<T> threadLocalInstance = new ThreadLocal<T>();

	private T defaultInstance;

	ThreadLocalInstanceSupplier(T defaultInstance)
	{
		this.defaultInstance = defaultInstance;
	}

	public void set(T instance)
	{
		threadLocalInstance.set(instance);
	}

	public void clear()
	{
		threadLocalInstance.remove();
	}

	public T get()
	{
		T instance = threadLocalInstance.get();
		if (instance != null)
		{
			return instance;
		}

		return defaultInstance;
	}
}
