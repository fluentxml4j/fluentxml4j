package com.github.fluentxml4j.internal.validate;

import com.github.fluentxml4j.validate.Severity;
import com.github.fluentxml4j.validate.ValidateNode;
import com.github.fluentxml4j.validate.ValidationError;
import com.github.fluentxml4j.validate.ValidationResult;
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

class ValidateNodeImpl implements ValidateNode
{
	private Document doc;
	private List<Document> schemaDocs = new ArrayList<>();

	ValidateNodeImpl(Document doc)
	{
		this.doc = doc;
	}

	@Override
	public ValidateNode againstSchema(Document schemaDoc)
	{
		schemaDocs.add(schemaDoc);
		return this;
	}

	@Override
	public ValidationResult getResult()
	{
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		try
		{
			List<ValidationError> errors = new ArrayList<>();
			for (Document schemaDoc : this.schemaDocs)
			{
				Schema schema = schemaFactory.newSchema(new DOMSource(schemaDoc));

				Validator validator = schema.newValidator();
				validator.setErrorHandler(new ErrorHandler()
				{
					@Override
					public void warning(SAXParseException e) throws SAXException
					{
						errors.add(new ValidationError(Severity.WARNING, e.getMessage(), e.getSystemId(), e.getPublicId(), e.getColumnNumber(), e.getLineNumber()));
					}

					@Override
					public void error(SAXParseException e) throws SAXException
					{
						errors.add(new ValidationError(Severity.ERROR, e.getMessage(), e.getSystemId(), e.getPublicId(), e.getColumnNumber(), e.getLineNumber()));
					}

					@Override
					public void fatalError(SAXParseException e) throws SAXException
					{
						errors.add(new ValidationError(Severity.FATAL_ERROR, e.getMessage(), e.getSystemId(), e.getPublicId(), e.getColumnNumber(), e.getLineNumber()));
					}
				});
				validator.validate(new DOMSource(this.doc));
			}
			return new ValidationResult(errors);
		}
		catch (SAXException | IOException e)
		{
			return new ValidationResult(new ArrayList<>());
		}
	}
}
