## XPath Query Examples

### Select elements and iterate via for loop
```
for(Element element : from(doc).selectElements("//H1[text()='Some Title']") ) {
  ...
}
```

### Select elements and iterate via forEach

```
from(doc).selectElements("//*").forEach( (e) -> { ... } );
```

### Select elements into list
```
List<Element> elements = from(doc).selectElements("//*").asList();
```

### Select all words of a document
```
Set<String> words = from(doc)
   .selectStrings("//text()")
   .asStream()
   .flatMap((s) -> Arrays.stream(s.split("[\\s]+")))
   .collect(Collectors.toSet());
```