package com.github.fluentxml4j.junit;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.events.XMLEvent;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

class XMLEventWriterProxy
{
	public static XMLEventWriter proxyFor(XMLEventWriter delegate, boolean autoFlush)
	{
		return (XMLEventWriter) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
				new Class[]{XMLEventWriter.class},
				new InvocationHandler()
				{
					private boolean closed = false;

					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
					{
						this.closed = this.closed || "close".equals(method.getName());
						if (autoFlush && "add".equals(method.getName()) && args.length == 1 && args[0] instanceof XMLEvent)
						{
							XMLEvent event = (XMLEvent) args[0];
							Object result = method.invoke(delegate, args);
							if (event != null && event.isEndDocument())
							{
								delegate.flush();
							}
							return result;
						}
						else if (autoFlush && "add".equals(method.getName()) && args.length == 1 && args[0] instanceof XMLEventReader)
						{

							XMLEventReader rd = (XMLEventReader) args[0];
							while (rd.hasNext())
							{
								XMLEvent event = rd.nextEvent();
								((XMLEventWriter) proxy).add(event);
							}

							return null;
						}
						else
						{
							return method.invoke(delegate, args);
						}
					}
				});
	}
}
