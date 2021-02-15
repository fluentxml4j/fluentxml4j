package com.github.fluentxml4j.internal.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.xml.stream.Location;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.stax.StAXSource;
import java.io.OutputStream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StaxUtilsTest
{
	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Mock
	private XMLEventReader xmlEventReader;

	@Mock
	private XMLEvent event;

	@Mock
	private Location location;

	@Mock
	private OutputStream outputStream;

	@Test
	public void newStAXSource() throws Exception
	{
		givenXMLEventReaderAtStartOfDocument();

		StAXSource source = StaxUtils.newStAXSource(xmlEventReader);

		assertThat(source, is(not(nullValue())));
	}

	@Test
	public void newStAXSourceWithXMLEventReaderInInvalidState() throws Exception
	{
		expectedException.expect(IllegalStateException.class);
		givenXMLEventReaderAtEndOfDocument();

		StaxUtils.newStAXSource(xmlEventReader);
	}

	private void givenXMLEventReaderAtEndOfDocument() throws XMLStreamException
	{
		when(xmlEventReader.peek()).thenReturn(event);
		when(event.getEventType()).thenReturn(XMLEvent.END_DOCUMENT);
	}

	private void givenXMLEventReaderAtStartOfDocument() throws XMLStreamException
	{
		when(xmlEventReader.peek()).thenReturn(event);
		when(event.getEventType()).thenReturn(XMLEvent.START_DOCUMENT);
		when(event.getLocation()).thenReturn(this.location);
	}

	@Test
	public void newXMLStreamWriter() throws Exception
	{
		XMLStreamWriter writer = StaxUtils.newXMLStreamWriter(outputStream);

		assertThat(writer, is(not(nullValue())));
	}

	@Test
	public void newXMLEventWriter() throws Exception
	{
		XMLEventWriter writer = StaxUtils.newXMLEventWriter(outputStream);

		assertThat(writer, is(not(nullValue())));
	}

}
