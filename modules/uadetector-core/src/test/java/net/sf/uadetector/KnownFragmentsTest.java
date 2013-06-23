package net.sf.uadetector;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Adam Jordens (adam@jordens.org)
 */
public class KnownFragmentsTest {
    @Test
    public void contains_EmptyKnownFragments() {
        Assert.assertFalse(KnownFragments.EMPTY.containsAny(KnownFragment.IPAD));
    }

    @Test
    public void contains_iPad() {
        final String userAgent = "Mozilla/5.0 (iPad; U; CPU OS 4_2_1 like Mac OS X; ja-jp) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8C148 Safari/6533.18.5";
        Assert.assertTrue(new KnownFragments(userAgent).containsAny(KnownFragment.IPAD));
    }
}
