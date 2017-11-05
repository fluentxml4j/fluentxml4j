## Parsing Examples

### Parse from input stream
```
Document doc = parse(getClass().getResourceAsStream("example.xml")).document();
```

or

```
Document doc = from(getClass().getResourceAsStream("example.xml")).parse().document();
```
