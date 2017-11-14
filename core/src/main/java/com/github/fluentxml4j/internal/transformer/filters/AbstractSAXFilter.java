package com.github.fluentxml4j.internal.transformer.filters;

import com.github.fluentxml4j.FluentXmlConfigurationException;
import com.github.fluentxml4j.xpath.ImmutableNamespaceContext;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.ext.LexicalHandler;

import javax.xml.transform.ErrorListener;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import java.util.Properties;

public abstract class AbstractSAXFilter extends Transformer implements TransformerHandler
{
	protected ContentHandler nextContentHandler;
	protected LexicalHandler nextLexicalHandler;
	protected DTDHandler nextDtdHandler;

	protected ImmutableNamespaceContext namespaceContext = new ImmutableNamespaceContext();

	protected String systemId;

	protected AbstractSAXFilter()
	{
		this(null);
	}

	protected AbstractSAXFilter(ContentHandler nextContentHandler)
	{
		setResult(new SAXResult(nextContentHandler));
	}

	@Override
	public void setDocumentLocator(Locator locator)
	{
		boolean process = beforeSetDocumentLocator(locator);
		if (process)
		{
			delegateSetDocumentLocator(locator);
		}

		afterSetDocumentLocator(locator, process);
	}

	protected void delegateSetDocumentLocator(Locator locator)
	{
		this.nextContentHandler.setDocumentLocator(locator);
	}

	protected boolean beforeSetDocumentLocator(Locator locator)
	{
		return true;
	}

	protected void afterSetDocumentLocator(Locator locator, boolean processed)
	{
	}

	@Override
	public void startDocument() throws SAXException
	{
		boolean process = beforeStartDocument();
		if (process)
		{
			delegateStartDocument();
		}
		afterStartDocument(process);
	}


	protected void delegateStartDocument() throws SAXException
	{
		this.nextContentHandler.startDocument();
	}

	protected void afterStartDocument(boolean processed) throws SAXException
	{
	}

	protected boolean beforeStartDocument() throws SAXException
	{
		return true;
	}

	@Override
	public void endDocument() throws SAXException
	{
		boolean process = beforeEndDocument();
		if (process)
		{
			delegateEndDocument();
		}

		afterEndDocument(process);
	}

	protected void delegateEndDocument() throws SAXException
	{
		this.nextContentHandler.endDocument();
	}

	protected void afterEndDocument(boolean processed) throws SAXException
	{
	}

	protected boolean beforeEndDocument() throws SAXException
	{
		return true;
	}

	@Override
	public void startPrefixMapping(String prefix, String nsURI) throws SAXException
	{
		boolean process = beforeStartPrefixMapping(prefix, nsURI);
		if (process)
		{
			this.namespaceContext = this.namespaceContext.addMapping(prefix, nsURI);
			delegateStartPrefixMapping(prefix, nsURI);
		}
		afterStartPrefixMapping(prefix, nsURI, process);
	}

	protected void afterStartPrefixMapping(String prefix, String nsURI, boolean processed)
	{
	}

	protected boolean beforeStartPrefixMapping(String prefix, String nsURI)
	{
		return true;
	}

	protected void delegateStartPrefixMapping(String prefix, String nsURI) throws SAXException
	{
		this.nextContentHandler.startPrefixMapping(prefix, nsURI);
	}

	@Override
	public void endPrefixMapping(String prefix) throws SAXException
	{
		boolean process = beforeEndPrefixMapping(prefix);
		if (process)
		{
			delegateEndPrefixMapping(prefix);
			this.namespaceContext = this.namespaceContext.removePrefix(prefix);
		}
		afterEndPrefixMapping(prefix, process);

	}

	protected void afterEndPrefixMapping(String prefix, boolean processed)
	{
	}

	protected boolean beforeEndPrefixMapping(String prefix)
	{
		return true;
	}

	protected void delegateEndPrefixMapping(String prefix) throws SAXException
	{
		this.nextContentHandler.endPrefixMapping(prefix);
	}

	@Override
	public void startElement(String nsURI, String localName, String qName, Attributes attributes) throws SAXException
	{
		boolean process = beforeStartElement(nsURI, localName, qName, attributes);
		if (process)
		{
			delegateStartElement(nsURI, localName, qName, attributes);
		}
		afterStartElement(nsURI, localName, qName, attributes, process);
	}

	protected void afterStartElement(String nsURI, String localName, String qName, Attributes attributes, boolean processed)
	{
	}

	protected boolean beforeStartElement(String nsURI, String localName, String qName, Attributes attributes)
	{
		return true;
	}

	protected void delegateStartElement(String nsURI, String localName, String qName, Attributes attributes) throws SAXException
	{
		this.nextContentHandler.startElement(nsURI, localName, qName, attributes);
	}

	@Override
	public void endElement(String nsURI, String localName, String qName) throws SAXException
	{
		boolean process = beforeEndElement(nsURI, localName, qName);
		if (process)
		{
			delegateEndElement(nsURI, localName, qName);
		}
		afterEndElement(nsURI, localName, qName, process);
	}

	protected void afterEndElement(String nsURI, String localName, String qName, boolean processed)
	{
	}

	protected boolean beforeEndElement(String nsURI, String localName, String qName)
	{
		return true;
	}

	protected void delegateEndElement(String nsURI, String localName, String qName) throws SAXException
	{
		this.nextContentHandler.endElement(nsURI, localName, qName);
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException
	{
		boolean process = beforeCharacters(ch, start, length);
		if (process)
		{
			delegateCharacters(ch, start, length);
		}
		afterCharacters(ch, start, length, process);
	}

	protected boolean beforeCharacters(char[] ch, int start, int length)
	{
		return true;
	}

	protected void afterCharacters(char[] ch, int start, int length, boolean processed)
	{
	}

	protected void delegateCharacters(char[] ch, int start, int length) throws SAXException
	{
		this.nextContentHandler.characters(ch, start, length);
	}

	@Override
	public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException
	{
		boolean process = beforeIgnorableWhitespace(ch, start, length);
		if (process)
		{
			delegateIgnorableWhitespace(ch, start, length);
		}
		afterIgnorableWhitespace(ch, start, length, process);
	}

	protected void afterIgnorableWhitespace(char[] ch, int start, int length, boolean processed)
	{
	}

	protected boolean beforeIgnorableWhitespace(char[] ch, int start, int length)
	{
		return true;
	}

	protected void delegateIgnorableWhitespace(char[] ch, int start, int length) throws SAXException
	{
		this.nextContentHandler.ignorableWhitespace(ch, start, length);
	}

	@Override
	public void processingInstruction(String target, String data) throws SAXException
	{
		boolean process = beforeProcessingInstruction(target, data);
		if (process)
		{
			delegateProcessingInstruction(target, data);
		}
		afterProcessingInstruction(target, data, process);
	}

	protected void afterProcessingInstruction(String target, String data, boolean process)
	{
	}

	protected boolean beforeProcessingInstruction(String target, String data)
	{
		return true;
	}

	protected void delegateProcessingInstruction(String target, String data) throws SAXException
	{
		this.nextContentHandler.processingInstruction(target, data);
	}

	@Override
	public void skippedEntity(String name) throws SAXException
	{
		boolean process = beforeSkippedEntity(name);
		if (process)
		{
			this.nextContentHandler.skippedEntity(name);
		}
		afterSkippedEntity(name, process);
	}

	protected boolean beforeSkippedEntity(String name)
	{
		return true;
	}

	protected void afterSkippedEntity(String name, boolean processed)
	{
	}

	@Override
	public Transformer getTransformer()
	{
		return this;
	}

	@Override
	public void setResult(Result result) throws IllegalArgumentException
	{
		SAXResult saxResult = toSAXResult(result);

		this.nextContentHandler = saxResult.getHandler();
	}

	private SAXResult toSAXResult(Result result)
	{
		if (result instanceof SAXResult)
		{
			return (SAXResult) result;
		}

		try
		{
			SAXTransformerFactory transformerFactory = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
			TransformerHandler transformerHandler = transformerFactory.newTransformerHandler();
			transformerHandler.setResult(result);
			return new SAXResult(transformerHandler);
		}
		catch (TransformerConfigurationException ex)
		{
			throw new FluentXmlConfigurationException(ex);
		}
	}

	@Override
	public void setSystemId(String systemId)
	{
		this.systemId = systemId;
	}

	@Override
	public String getSystemId()
	{
		return this.systemId;
	}

	@Override
	public void notationDecl(String name, String publicId, String systemId) throws SAXException
	{
		boolean process = beforeNotationDecl(name, publicId, systemId);
		if (process)
		{
			delegateNotationDecl(name, publicId, systemId);
		}
		afterNotationDecl(name, publicId, systemId, process);
	}

	protected void afterNotationDecl(String name, String publicId, String systemId, boolean processed)
	{
	}

	protected boolean beforeNotationDecl(String name, String publicId, String systemId)
	{
		return true;
	}

	protected void delegateNotationDecl(String name, String publicId, String systemId) throws SAXException
	{
		if (this.nextDtdHandler == null)
		{
			return;
		}

		this.nextDtdHandler.notationDecl(name, publicId, systemId);
	}

	@Override
	public void unparsedEntityDecl(String name, String publicId, String systemId, String notationName) throws SAXException
	{
		boolean process = beforeUnparsedEntityDecl(name, publicId, systemId, notationName);
		if (process)
		{
			delegateUnparsedEntityDecl(name, publicId, systemId, notationName);
		}
		afterUnparsedEntityDecl(name, publicId, systemId, notationName, process);
	}

	protected void afterUnparsedEntityDecl(String name, String publicId, String systemId, String notationName, boolean processed)
	{
	}

	protected boolean beforeUnparsedEntityDecl(String name, String publicId, String systemId, String notationName)
	{
		return true;
	}

	protected void delegateUnparsedEntityDecl(String name, String publicId, String systemId, String notationName) throws SAXException
	{
		if (this.nextDtdHandler == null)
		{
			return;
		}

		this.nextDtdHandler.unparsedEntityDecl(name, publicId, systemId, notationName);
	}

	@Override
	public void startDTD(String name, String publicId, String systemId) throws SAXException
	{
		boolean process = beforeStartDTD(name, publicId, systemId);
		if (process)
		{
			delegateStartDTD(name, publicId, systemId);
		}
		afterStartDTD(name, publicId, systemId, process);
	}

	protected void afterStartDTD(String name, String publicId, String systemId, boolean processed)
	{
	}

	protected boolean beforeStartDTD(String name, String publicId, String systemId)
	{
		return true;
	}

	protected void delegateStartDTD(String name, String publicId, String systemId) throws SAXException
	{
		if (this.nextLexicalHandler == null)
		{
			return;
		}

		this.nextLexicalHandler.startDTD(name, publicId, systemId);
	}

	@Override
	public void endDTD() throws SAXException
	{
		boolean process = beforeEndDTD();
		if (process)
		{
			delegateEndDTD();
		}
		afterEndDTD(process);
	}

	protected void afterEndDTD(boolean processed)
	{
	}

	protected boolean beforeEndDTD()
	{
		return true;
	}

	protected void delegateEndDTD() throws SAXException
	{
		if (this.nextLexicalHandler == null)
		{
			return;
		}

		this.nextLexicalHandler.endDTD();
	}

	@Override
	public void startEntity(String name) throws SAXException
	{
		boolean process = beforeStartEntity(name);
		if (process)
		{
			delegateStartEntity(name);
		}
		afterStartEntity(name, process);
	}

	protected void afterStartEntity(String name, boolean processed)
	{
	}

	protected boolean beforeStartEntity(String name)
	{
		return true;
	}

	protected void delegateStartEntity(String name) throws SAXException
	{
		if (this.nextLexicalHandler == null)
		{
			return;
		}

		this.nextLexicalHandler.startEntity(name);
	}

	@Override
	public void endEntity(String name) throws SAXException
	{
		boolean process = beforeEndEntity(name);
		if (process)
		{
			delegateEndEntity(name);
		}
		afterEndEntity(name, process);
	}

	protected void afterEndEntity(String name, boolean process)
	{
	}

	protected boolean beforeEndEntity(String name)
	{
		return true;
	}

	protected void delegateEndEntity(String name) throws SAXException
	{
		if (this.nextLexicalHandler == null)
		{
			return;
		}

		this.nextLexicalHandler.endEntity(name);
	}

	@Override
	public void startCDATA() throws SAXException
	{
		boolean process = beforeStartCDATA();
		if (process)
		{
			deleateStartCDATA();
		}
		afterStartCDATA(process);
	}

	protected void afterStartCDATA(boolean processed)
	{
	}

	protected boolean beforeStartCDATA()
	{
		return true;
	}

	protected void deleateStartCDATA() throws SAXException
	{
		if (this.nextLexicalHandler == null)
		{
			return;
		}

		this.nextLexicalHandler.startCDATA();
	}

	@Override
	public void endCDATA() throws SAXException
	{
		boolean process = beforeEndCDATA();
		if (process)
		{
			delegateEndCDATA();
		}
		afterEndCDATA(process);
	}

	protected void afterEndCDATA(boolean processed)
	{
	}

	protected boolean beforeEndCDATA()
	{
		return true;
	}

	protected void delegateEndCDATA() throws SAXException
	{
		if (this.nextLexicalHandler == null)
		{
			return;
		}

		this.nextLexicalHandler.endCDATA();
	}

	@Override
	public void comment(char[] ch, int start, int length) throws SAXException
	{
		boolean process = beforeComment(ch, start, length);
		if (process)
		{
			delegateComment(ch, start, length);
		}
		afterComment(ch, start, length, process);
	}

	private void afterComment(char[] ch, int start, int length, boolean processed)
	{
	}

	protected boolean beforeComment(char[] ch, int start, int length)
	{
		return true;
	}

	protected void delegateComment(char[] ch, int start, int length) throws SAXException
	{
		if (this.nextLexicalHandler == null)
		{
			return;
		}

		this.nextLexicalHandler.comment(ch, start, length);
	}

	@Override
	public void transform(Source source, Result result) throws TransformerException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void setParameter(String name, Object o)
	{
		throw new IllegalArgumentException("Unsupported param " + name + ".");
	}

	@Override
	public Object getParameter(String name)
	{
		throw new IllegalArgumentException("Unsupported param " + name + ".");
	}

	@Override
	public void clearParameters()
	{
	}

	@Override
	public void setURIResolver(URIResolver uriResolver)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public URIResolver getURIResolver()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void setOutputProperties(Properties properties)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Properties getOutputProperties()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void setOutputProperty(String s, String s1) throws IllegalArgumentException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public String getOutputProperty(String s) throws IllegalArgumentException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void setErrorListener(ErrorListener errorListener) throws IllegalArgumentException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public ErrorListener getErrorListener()
	{
		throw new UnsupportedOperationException();
	}
}
