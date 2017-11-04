package com.github.fluentxml4j.internal.validator;

import com.github.fluentxml4j.DocumentTestRule;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FluentXmlValidatorIntegrationTest
{
	@Rule
	public DocumentTestRule xsdDocumentTestRule = new DocumentTestRule("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
			"<xs:schema xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">\n" +
			"<xs:element name=\"root\" type=\"xs:string\"/>\n" +
			"</xs:schema>");
	@Rule
	public DocumentTestRule documentTestRule = new DocumentTestRule("<root/>");

	@Rule
	public DocumentTestRule invalidDocumentTestRule = new DocumentTestRule("<invalid/>");

	private FluentXmlValidator fluentXmlValidator = new FluentXmlValidator();

	@Test
	public void validate()
	{
		boolean valid = fluentXmlValidator
				.validate(documentTestRule.document())
				.withSchema(xsdDocumentTestRule.document())
				.result()
				.isValid();

		assertThat(valid, is(true));
	}

	@Test
	public void validateInvalid()
	{
		boolean valid = fluentXmlValidator
				.validate(invalidDocumentTestRule.document())
				.withSchema(xsdDocumentTestRule.document())
				.result()
				.isValid();

		assertThat(valid, is(false));
	}
}