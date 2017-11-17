package com.github.fluentxml4j;

import com.github.fluentxml4j.internal.parse.FluentXmlParser;
import com.github.fluentxml4j.internal.serialize.FluentXmlSerializer;
import com.github.fluentxml4j.internal.transform.FluentXmlTransformer;
import com.github.fluentxml4j.internal.query.FluentQuery;
import org.junit.rules.ExternalResource;

public class FluentXmlInjectionRule extends ExternalResource
{
	static
	{
		FluentXml.fluentXmlSerializer = new ThreadLocalInstanceSupplier<>(FluentXml.fluentXmlSerializer.get());
		FluentXml.fluentQuery = new ThreadLocalInstanceSupplier<>(FluentXml.fluentQuery.get());
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

	public void setFluentXPath(FluentQuery instance)
	{
		((ThreadLocalInstanceSupplier<FluentQuery>) FluentXml.fluentQuery).set(instance);
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
		((ThreadLocalInstanceSupplier<FluentQuery>) FluentXml.fluentQuery).clear();
		((ThreadLocalInstanceSupplier<FluentXmlTransformer>) FluentXml.fluentXmlTransformer).clear();

		super.after();
	}
}
