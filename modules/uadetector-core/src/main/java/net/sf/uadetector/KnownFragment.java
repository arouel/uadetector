package net.sf.uadetector;

import net.sf.qualitycheck.Check;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Adam Jordens (adam@jordens.org)
 */
public enum KnownFragment {
    /**
     * iPad
     */
    IPAD("iPad", Pattern.compile(".*iPad.*")),

    /**
     * iPhone
     */
    IPHONE("iPhone", Pattern.compile(".*iPhone.*"));

    /**
     * This method will try to find all known fragments containing within a user agent.
     *
     * @param userAgent complete user agent
     * @return all matching KnownFragement's contained within the user agent
     * @throws net.sf.qualitycheck.exception.IllegalNullArgumentException
     *          if the given argument is {@code null}
     */
    public static Set<KnownFragment> evaluate(@Nonnull final String userAgent) {
        Check.notNull(userAgent, "userAgent");

        Set<KnownFragment> knownFragments = new HashSet<KnownFragment>();

        for (final KnownFragment value : values()) {
            final Matcher m = value.getPattern().matcher(userAgent);
            if (m.matches()) {
                knownFragments.add(value);
            }
        }

        return Collections.unmodifiableSet(knownFragments);
    }

    /**
     * The internal fragment name.
     */
    @Nonnull
    private final String name;

    /**
     * The regular expression which a fragment must be match.
     */
    @Nonnull
    private final Pattern pattern;

    private KnownFragment(@Nonnull final String name, @Nonnull final Pattern pattern) {
        this.name = name;
        this.pattern = pattern;
    }

    /**
     * Gets the internal fragment name.
     *
     * @return the internal fragment name
     */
    @Nonnull
    public String getName() {
        return this.name;
    }

    /**
     * Gets the regular expression which a fragment name must be match with.
     *
     * @return regular expression
     */
    @Nonnull
    public Pattern getPattern() {
        return pattern;
    }
}