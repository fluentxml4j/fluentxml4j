# This is FluentX, a fluent API for XML querying in Java

## Requirements
FluentX uses the builtin JDK XML parser and XPath API, but Java 8 is required because of heavy use
of the streaming API and Optional.

## Installation from Maven Central

```
<dependency>
  <groupId>com.github.cbuschka.fluentx</groupId>
  </artifactId>fluentx</artifactId>
  <version>1.0.0</version>
</dependency>
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
Iterable<Element> elements = 
 from(doc)
   .selectStrings("//text()")
   .asStream()
   .flatMap((s) -> Arrays.stream(s.split("[\\s\\t]+")))
   .collect(Collectors.toSet());
```
