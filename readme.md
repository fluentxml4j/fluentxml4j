# This is FluentXML4J, a fluent API for XML querying in Java [![Build Status](https://travis-ci.org/fluentxml4j/fluentxml4j.svg?branch=master)](https://travis-ci.org/fluentxml4j/fluentxml4j)

## Requirements
FluentXML4J uses the builtin JDK XML parser and XPath API, but Java 8 is required because of heavy use
of the streaming API and Optional.

## XPath Query Examples

### Select elements
```
Iterable<Element> elements = 
 from(doc)
   .selectElements("//H1[text()='Some Title']");
```

### Select all words of a document
```
Set<String> words = 
 from(doc)
   .selectStrings("//text()")
   .asStream()
   .flatMap((s) -> Arrays.stream(s.split("[\\s\\t]+")))
   .collect(Collectors.toSet());
```
