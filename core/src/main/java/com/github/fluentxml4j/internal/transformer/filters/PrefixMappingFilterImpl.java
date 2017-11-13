package com.github.fluentxml4j.internal.transformer.filters;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class PrefixMappingFilterImpl extends AbstractSAXFilter
{
	private PrefixMapper prefixReplacer = new PrefixMapper();

	public void addPrefixMapping(String prefix, String newPrefix)
	{
		this.prefixReplacer.addPrefixMapping(prefix, newPrefix);
	}

	@Override
	protected void delegateStartElement(String nsURI, String localName, String qName, Attributes attributes) throws SAXException
	{
		ElementName newElementName = this.prefixReplacer.mapElementName(nsURI, qName, namespaceContext);

		super.delegateStartElement(nsURI, newElementName.localName(), newElementName.qName(), attributes);
	}

	@Override
	protected void delegateEndElement(String nsURI, String localName, String qName) throws SAXException
	{
		ElementName newElementName = this.prefixReplacer.mapElementName(nsURI, qName, namespaceContext);

		super.delegateEndElement(nsURI, newElementName.localName(), newElementName.qName());
	}
}
