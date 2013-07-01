package net.sf.uadetector.example;

import java.util.concurrent.TimeUnit;

import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentStringParser;
import net.sf.uadetector.service.UADetectorServiceFactory;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class CachedUserAgentStringParser implements UserAgentStringParser {

	private final Cache<String, ReadableUserAgent> cache = CacheBuilder.newBuilder().maximumSize(100).expireAfterWrite(2, TimeUnit.HOURS)
			.build();

	private final UserAgentStringParser parser = UADetectorServiceFactory.getCachingAndUpdatingParser();

	@Override
	public String getDataVersion() {
		return parser.getDataVersion();
	}

	@Override
	public ReadableUserAgent parse(final String userAgentString) {
		ReadableUserAgent result = cache.getIfPresent(userAgentString);
		if (result == null) {
			result = parser.parse(userAgentString);
			cache.put(userAgentString, result);
		}
		return result;
	}

	@Override
	public void shutdown() {
		parser.shutdown();
	}

}
