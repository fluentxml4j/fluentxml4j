package com.github.fluentxml4j.junit;

import javax.xml.stream.XMLStreamWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

class XMLStreamWriterProxy
{
	public static XMLStreamWriter proxyFor(XMLStreamWriter delegate, boolean autoFlush)
	{
		return (XMLStreamWriter) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
				new Class[]{XMLStreamWriter.class},
				new InvocationHandler()
				{
					private boolean closed = false;

					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
					{
						this.closed = this.closed || "close".equals(method.getName());
						if (autoFlush && "writeEndDocument".equals(method.getName()))
						{
							Object result = method.invoke(delegate, args);
							delegate.flush();
							return result;
						}
						else
						{
							return method.invoke(delegate, args);
						}
					}
				});
	}
}
