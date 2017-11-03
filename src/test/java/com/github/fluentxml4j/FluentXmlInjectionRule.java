package com.github.fluentxml4j;

import com.github.fluentxml4j.parser.FluentXmlParser;
import com.github.fluentxml4j.serializer.FluentXmlSerializer;
import com.github.fluentxml4j.transformer.FluentXmlTransformer;
import com.github.fluentxml4j.xpath.FluentXPath;
import org.junit.rules.ExternalResource;

public class FluentXmlInjectionRule extends ExternalResource
{
	static
	{
		FluentXml.fluentXmlSerializer = new ThreadLocalInstanceSupplier<>(FluentXml.fluentXmlSerializer.get());
		FluentXml.fluentXPath = new ThreadLocalInstanceSupplier<>(FluentXml.fluentXPath.get());
		FluentXml.fluentXmlParser = new ThreadLocalInstanceSupplier<>(FluentXml.fluentXmlParser.get());
		FluentXml.fluentXmlTransformer = new ThreadLocalInstanceSupplier<>(FluentXml.fluentXmlTransformer.get());
	}

	public void setFluentXmlSerializer(FluentXmlSerializer instance)
	{
		((ThreadLocalInstanceSupplier<FluentXmlSerializer>) FluentXml.fluentXmlSerializer).set(instance);
	}

	public void setFluentXmlParser(FluentXmlParser instance)
	{
		((ThreadLocalInstanceSupplier<FluentXmlParser>) FluentXml.fluentXmlParser).set(instance);
	}

	public void setFluentXPath(FluentXPath instance)
	{
		((ThreadLocalInstanceSupplier<FluentXPath>) FluentXml.fluentXPath).set(instance);
	}

	public void setFluentXmlTransformer(FluentXmlTransformer instance)
	{
		((ThreadLocalInstanceSupplier<FluentXmlTransformer>) FluentXml.fluentXmlTransformer).set(instance);
	}

	@Override
	protected void after()
	{
		((ThreadLocalInstanceSupplier<FluentXmlSerializer>) FluentXml.fluentXmlSerializer).clear();
		((ThreadLocalInstanceSupplier<FluentXmlParser>) FluentXml.fluentXmlParser).clear();
		((ThreadLocalInstanceSupplier<FluentXPath>) FluentXml.fluentXPath).clear();
		((ThreadLocalInstanceSupplier<FluentXmlTransformer>) FluentXml.fluentXmlTransformer).clear();

		super.after();
	}
}
