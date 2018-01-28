# Changelog

## #2.2.0
* Added module util
* Added transform(Source)
* Added serialize(Source)

## #2.1.0
* Added count()/empty(), #28
* Added validation support via and without transformation chain
* Added XmlSource.asString()
* Added serialize/transform toBytes()
* Better usage examples
* Changed query from(Document) to from(Node)
* Added selectBoolean(s)/selectInteger(s), #26, #27

2.0.0
* Added parse test for URL and File
* Renamed packages to parse, transform, serialize and query
* Added JAXB support, #7
* Refactored namespace context handling
* Added transform(Url), #21
* Added missing FluentXml.transform(File), #18
* Added serialize.to(File), #22
* Added withStylesheet(File), #23
* Renamed fluentxml4j module to fluentxml4j-core
* Renamed xpath.FromNode to xpath.QueryFromNode
* AbstractSAXFilter now adapts to non sax results via interim trax transformer
* Added filter for element prefix mapping
* Added StAX support for transform and serialize
* Restructured into multi module project with core and junit module, #6
* Added parse/from URL, #20
* Added support for parsing from file, #19
* Introduced FluentXmlProcessingException and FluentXmlConfigurationException
* Introduced package internal
* Added transformation to streams with serializer suppoer - changed result of SerializerConfigurer from Transformer to TransformerHandler; incompatible API change!
* Refactored transformer chaining
* Improved transformation example

1.0.3
* Added support for transformation
* Added a private constructor to entry point FluentXml
* Added example for beginning parsing with from()
* Added API to begin parsing with from(InputStream|InputSource|Reader)
* Added sonar analysis
* Added examples for parsing and serialization
* Added jacoco plugin and codecov.io hook for travis

1.0.2
* FluentXml now main entry point
* Added test for DocumentBuilderConfigurerAdapter
* Added test for serializer customization

1.0.2
* Added XPathConfigurer for query customization
* Added SerializerConfigurer for serializer customization
* Added DocumentBuilderConfigurer for parser customization
* Added main entry point for using FluentXml4J
* Added fluent api for serialization
* Added fluent api for parsing
* Result with multiple elements now is instance of Iterable

1.0.0
* license Apache #2.0
* initial version
