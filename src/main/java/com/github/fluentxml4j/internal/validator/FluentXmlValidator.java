package com.github.fluentxml4j.internal.validator;

import com.github.fluentxml4j.FluentXmlProcessingException;
import com.github.fluentxml4j.validator.Severity;
import com.github.fluentxml4j.validator.ValidateNode;
import com.github.fluentxml4j.validator.ValidateWithSchemaNode;
import com.github.fluentxml4j.validator.ValidationResultItem;
import com.github.fluentxml4j.validator.ValidationResultNode;
import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.XMLConstants;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FluentXmlValidator
{
	public ValidateNode validate(Document doc)
	{
		return new ValidateNode()
		{
			@Override
			public ValidateWithSchemaNode withSchema(Document schemaDoc)
			{
				return new ValidateWithSchemaNode()
				{
					@Override
					public ValidationResultNode result()
					{
						return new ValidationResultNode()
						{
							@Override
							public boolean isValid()
							{
								return asList().isEmpty();
							}

							@Override
							public List<ValidationResultItem> asList()
							{
								try
								{
									SchemaFactory factory =
											SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
									Schema schema = factory.newSchema(new DOMSource(schemaDoc));
									Validator validator = schema.newValidator();
									List<ValidationResultItem> validationResultItems = new ArrayList<>();
									validator.setErrorHandler(new ErrorHandler()
									{
										@Override
										public void warning(SAXParseException e) throws SAXException
										{
											validationResultItems.add(new ValidationResultItemImpl(Severity.WARNING, e.getMessage()));
										}

										@Override
										public void error(SAXParseException e) throws SAXException
										{
											validationResultItems.add(new ValidationResultItemImpl(Severity.ERROR, e.getMessage()));
										}

										@Override
										public void fatalError(SAXParseException e) throws SAXException
										{
											validationResultItems.add(new ValidationResultItemImpl(Severity.FATAL_ERROR, e.getMessage()));
										}
									});
									validator.validate(new DOMSource(doc));
									return validationResultItems;
								}
								catch (IOException | SAXException ex)
								{
									throw new FluentXmlProcessingException(ex);
								}
							}
						};
					}
				};
			}
		};
	}
}
