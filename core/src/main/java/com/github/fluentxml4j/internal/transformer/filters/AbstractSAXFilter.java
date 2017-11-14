package com.github.fluentxml4j.internal.transformer.filters;

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
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.sax.SAXResult;
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
		return (Transformer) this;
	}

	@Override
	public void setResult(Result result) throws IllegalArgumentException
	{
		if (!(result instanceof SAXResult))
		{
			throw new IllegalArgumentException("Only SAXResult supported.");
		}

		this.nextContentHandler = ((SAXResult) result).getHandler();
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
	public void notationDecl(String s, String s1, String s2) throws SAXException
	{
		if (this.nextDtdHandler != null)
		{
			this.nextDtdHandler.notationDecl(s, s1, s2);
		}
	}

	@Override
	public void unparsedEntityDecl(String s, String s1, String s2, String s3) throws SAXException
	{
		if (this.nextDtdHandler != null)
		{
			this.nextDtdHandler.unparsedEntityDecl(s, s1, s2, s3);
		}
	}

	@Override
	public void startDTD(String s, String s1, String s2) throws SAXException
	{
		if (this.nextLexicalHandler != null)
		{
			this.nextLexicalHandler.startDTD(s, s1, s2);
		}
	}

	@Override
	public void endDTD() throws SAXException
	{
		if (this.nextLexicalHandler != null)
		{
			this.nextLexicalHandler.endDTD();
		}
	}

	@Override
	public void startEntity(String s) throws SAXException
	{
		if (this.nextLexicalHandler != null)
		{
			this.nextLexicalHandler.startEntity(s);
		}
	}

	@Override
	public void endEntity(String s) throws SAXException
	{
		if (this.nextLexicalHandler != null)
		{
			this.nextLexicalHandler.endEntity(s);
		}
	}

	@Override
	public void startCDATA() throws SAXException
	{
		if (this.nextLexicalHandler != null)
		{
			this.nextLexicalHandler.startCDATA();
		}
	}

	@Override
	public void endCDATA() throws SAXException
	{
		if (this.nextLexicalHandler != null)
		{
			this.nextLexicalHandler.endCDATA();
		}
	}

	@Override
	public void comment(char[] ch, int start, int length) throws SAXException
	{
		if (this.nextLexicalHandler != null)
		{
			this.nextLexicalHandler.comment(ch, start, length);
		}
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
