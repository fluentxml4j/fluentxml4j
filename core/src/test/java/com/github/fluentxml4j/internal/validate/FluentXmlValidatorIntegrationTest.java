package com.github.fluentxml4j.internal.validate;

import com.github.fluentxml4j.junit.XmlSource;
import com.github.fluentxml4j.validate.ValidationResult;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FluentXmlValidatorIntegrationTest
{
	@Rule
	public XmlSource xmlSource = XmlSource.withData("<data><persons>" //
			+ "<person id='p1'/>" //
			+ "<person id='p2'/>" //
			+ "<person id='p3'/>" //
			+ "<person id='p4'/>" //
			+ "</persons></data>");

	@Rule
	public XmlSource acceptingSchemaSource = XmlSource.withDataFrom(FluentXmlValidatorIntegrationTest.class, "accepting-schema.xsd");
	@Rule
	public XmlSource rejectingSchemaSource = XmlSource.withDataFrom(FluentXmlValidatorIntegrationTest.class, "rejecting-schema.xsd");

	private FluentXmlValidator fluentXmlValidator = new FluentXmlValidator();

	@Test
	public void validateDocAgainstAcceptingSchemaDoc()
	{
		ValidationResult result = fluentXmlValidator.validate(xmlSource.asDocument())
				.againstSchema(acceptingSchemaSource.asDocument())
				.getResult();

		assertThat(result.hasErrors(), is(false));
		System.err.println(result.getErrors());
	}

	@Test
	public void validateDocAgainstRejectingSchemaDoc()
	{
		ValidationResult result = fluentXmlValidator.validate(xmlSource.asDocument())
				.againstSchema(rejectingSchemaSource.asDocument())
				.getResult();

		assertThat(result.hasErrors(), is(true));
		assertThat(result.getErrors().get(0).getDescription(), containsString("Invalid content was found starting with element 'person'. No child element is expected at this point."));
	}

	@Test
	public void validateDocAgainstAcceptingAndRejectingSchemaDoc()
	{
		ValidationResult result = fluentXmlValidator.validate(xmlSource.asDocument())
				.againstSchema(acceptingSchemaSource.asDocument())
				.againstSchema(rejectingSchemaSource.asDocument())
				.getResult();

		assertThat(result.hasErrors(), is(true));
		assertThat(result.getErrors().get(0).getDescription(), containsString("Invalid content was found starting with element 'person'. No child element is expected at this point."));
	}
}
