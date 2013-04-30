package net.sf.uadetector;

import net.sf.qualitycheck.Check;

import java.util.Collections;
import java.util.Set;

/**
 * @author Adam Jordens (adam@jordens.org)
 */
public class KnownFragments {
    private final Set<KnownFragment> contents;

    public static final KnownFragments EMPTY = new KnownFragments();

    private KnownFragments() {
        this.contents = Collections.<KnownFragment>emptySet();
    }

    public KnownFragments(String userAgent) {
        Check.notNull(userAgent);
        this.contents = KnownFragment.evaluate(userAgent);
    }

    public boolean containsAny(KnownFragment... knownFragments) {
        for (KnownFragment knownFragment : knownFragments) {
            if (contents.contains(knownFragment)) {
                return true;
            }
        }
        return false;
    }
}
