package com.github.fluentxml4j.examples.validate;

import com.github.fluentxml4j.junit.XmlSource;
import com.github.fluentxml4j.validate.ValidationResult;
import org.junit.Rule;
import org.junit.Test;

import static com.github.fluentxml4j.FluentXml.validate;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ValidateExample
{
	@Rule
	public XmlSource xmlSource = XmlSource.withData("<data><persons>" //
			+ "<person id='p1'><name>meiser</name><firstname>hans</firstname></person>" //
			+ "<person id='p2'><name>moeckl</name><firstname>joachim</firstname></person>" //
			+ "<person id='p3'><name>kerkeling</name><firstname>hans-peter</firstname></person>" //
			+ "</persons></data>");

	@Rule
	public XmlSource xsdSource = XmlSource.withDataFrom(ValidateExample.class, "schema.xsd");

	@Test
	public void validateDocAgainstDoc()
	{
		ValidationResult result = validate(xmlSource.asDocument())
				.againstSchema(xsdSource.asDocument())
				.getResult();

		assertThat(result.hasErrors(), is(false));
	}
}
