## Transformation Examples

### Transform via XSLT
```
Document resultDoc = transform(inputDoc)
        .withStylesheet(xsltInputStream)
        .toDocument();
```