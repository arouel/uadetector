package net.sf.uadetector;

public interface ReadableUserAgent {

	String getFamily();

	String getName();

	OperatingSystem getOperatingSystem();

	String getProducer();

	String getProducerUrl();

	String getType();

	String getUrl();

}
