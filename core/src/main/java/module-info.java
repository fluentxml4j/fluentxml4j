module com.github.fluentxml4j {
	exports com.github.fluentxml4j;
	exports com.github.fluentxml4j.parse;
	exports com.github.fluentxml4j.query;
	exports com.github.fluentxml4j.serialize;
	exports com.github.fluentxml4j.transform;
	exports com.github.fluentxml4j.validate;
	exports com.github.fluentxml4j.util.namespace;
	exports com.github.fluentxml4j.util.transform;

	requires java.activation;
	requires java.xml;
	requires java.xml.bind;
}
