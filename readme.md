# This is FluentXML4J, a fluent API for XML parsing, serialization and XPath querying in Java [![Build Status](https://travis-ci.org/fluentxml4j/fluentxml4j.svg?branch=master)](https://travis-ci.org/fluentxml4j/fluentxml4j) [![Test Coverage](https://codecov.io/gh/fluentxml4j/fluentxml4j/branch/master/graph/badge.svg)](https://codecov.io/gh/fluentxml4j/fluentxml4j) [![Maven Central](https://img.shields.io/maven-central/v/com.github.fluentxml4j/fluentxml4j.svg)](https://search.maven.org/#search%7Cga%7C1%7Cfluentxml4j) [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0.txt)

## Requirements
FluentXML4J uses the builtin JDK XML parser and XPath API, but Java 8 is required because of heavy use
of the streaming API and Optional.

## Maven Dependency

```
<dependency>
  <groupId>com.github.fluentxml4j</groupId>
  <artifactId>fluentxml4j</artifactId>
  <version>1.0.2</version>
</dependency>
```

## Parsing Examples

### Parse from input stream
```
Document doc = parse(getClass().getResourceAsStream("example.xml"))
 .document();
```

## Serialization Examples

### Serialize to string
```
String xml = serialize(document).toString();
```

### Serialize to System.err
```
serialize(document).to(System.err);
```

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

## License

[Apache License, Version 2.0](license)
