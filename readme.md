# FluentXML4J - a fluent API for XML in Java [![Build Status](https://travis-ci.org/fluentxml4j/fluentxml4j.svg?branch=master)](https://travis-ci.org/fluentxml4j/fluentxml4j) [![Quality Gate](https://sonarqube.com/api/badges/gate?key=com.github.fluentxml4j:fluentxml4j)](https://sonarcloud.io/dashboard?id=com.github.fluentxml4j%3Afluentxml4j) [![Maven Central](https://img.shields.io/maven-central/v/com.github.fluentxml4j/fluentxml4j.svg)](https://search.maven.org/#search%7Cga%7C1%7Cfluentxml4j) [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0.txt)

#### XML parsing, serialization XPath querying and transformation without boilerplate code

## Requirements
FluentXML4J uses the builtin JDK XML parser and XPath API, but Java 8 is required because of streaming API and Optional support.

## Maven Dependency

### FluentXML4J core library
```
<dependency>
  <groupId>com.github.fluentxml4j</groupId>
  <artifactId>fluentxml4j-core</artifactId>
  <version>2.0.0</version>
</dependency>
```

### FluentXML4J JUnit support library
```
<dependency>
  <groupId>com.github.fluentxml4j</groupId>
  <artifactId>fluentxml4j-junit</artifactId>
  <version>2.0.0</version>
</dependency>
```

## Examples

### Parsing
```
Document doc = parse(getClass().getResourceAsStream("example.xml"))
    .document();
```

[More parsing examples...](doc/examples/parsing-examples.md)


### Serialization
```
serialize(document).to(System.err);
```

[More serialization examples...](doc/examples/serialization-examples.md)

### Transformation
```
Document resultDoc = transform(inputDoc)
        .withStylesheet(xsltInputStream)
        .toDocument();
```

[More transformation examples...](doc/examples/transformation-examples.md)

### XPath Querying
```
List<Element> elements = from(doc)
    .selectElements("//*")
    .asList();
```

[More xpath examples...](doc/examples/xpath-examples.md)

## Contributing
FluentXML4J is an open source project, and contributions are welcome! Feel free to raise an issue or submit a pull request.

## License

[Apache License, Version 2.0](license)
