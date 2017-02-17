package net.sf.uadetector.filter;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.NotThreadSafe;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import net.sf.qualitycheck.Check;

@Immutable
public class Filter {

	/**
	 * whitelist filter: exclude all elements that do not match.
	 * blacklist filter: exclude all element that match.
	 */
	public enum Color {WHITE, BLACK};

	/**
	 * Accept everything filter. Specifies an empty blacklist
	 * for the browser.
	 */
	public static final Filter ALL = new Builder().build();

	@Nonnull
	private final Set<String> browsers;

	@Nonnull
	private final Color colorBrowsers;

	@NotThreadSafe
	public static final class Builder {

		private Set<String> browsers;

		private Color colorBrowsers;

		public Builder() {
			this.browsers = Collections.unmodifiableSet(new HashSet<String>());
			this.colorBrowsers = Color.BLACK;
		}

		@Nonnull
		public Builder setBrowsers(@Nonnull final Set<String> browsers) {
			Check.notNull(browsers, "browsers");
			this.browsers = Collections.unmodifiableSet(browsers);
			return this;
		}

		@Nonnull
		public Builder setColorBrowsers(@Nonnull final Color colorBrowsers) {
			Check.notNull(colorBrowsers, "colorBrowsers");
			this.colorBrowsers = colorBrowsers;
			return this;
		}

		public Filter build() {
			return new Filter(browsers, colorBrowsers);
		}

	}

	private Filter(final Set<String> browsers, final Color colorBrowsers) {
		this.browsers = browsers;
		this.colorBrowsers = colorBrowsers;
	}

	@Nonnull
	public Set<String> getBrowsers() { return browsers; }

	@Nonnull
	public Color getColorBrowsers() { return colorBrowsers; }

}
