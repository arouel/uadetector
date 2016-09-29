/*
 * Copyright 2014 Olivier Oudot
 *
 * THE WORK (AS DEFINED BELOW) IS PROVIDED UNDER THE TERMS OF THIS CREATIVE COMMONS PUBLIC LICENSE ("CCPL" OR
 * "LICENSE"). THE WORK IS PROTECTED BY COPYRIGHT AND/OR OTHER APPLICABLE LAW. ANY USE OF THE WORK OTHER THAN AS
 * AUTHORIZED UNDER THIS LICENSE OR COPYRIGHT LAW IS PROHIBITED.
 *
 * BY EXERCISING ANY RIGHTS TO THE WORK PROVIDED HERE, YOU ACCEPT AND AGREE TO BE BOUND BY THE TERMS OF THIS LICENSE.
 * TO THE EXTENT THIS LICENSE MAY BE CONSIDERED TO BE A CONTRACT, THE LICENSOR GRANTS YOU THE RIGHTS CONTAINED HERE IN
 * CONSIDERATION OF YOUR ACCEPTANCE OF SUCH TERMS AND CONDITIONS.
 *
 * https://creativecommons.org/licenses/by-sa/3.0/
 */
package net.sf.uadetector.internal.util;

import java.util.*;

public class NaturalOrderComparator implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        return compareNatural(o1 != null ? o1.toString() : "", o2 != null ? o2.toString() : "");
    }

    public static final int compareNatural(String s1, String s2) {
        // Skip all identical characters
        int len1 = s1.length();
        int len2 = s2.length();
        int i;
        char c1, c2;
        for (i = 0, c1 = 0, c2 = 0; (i < len1) && (i < len2) && (c1 = s1.charAt(i)) == (c2 = s2.charAt(i)); i++) ;

        // Check end of string
        if (c1 == c2)
            return (len1 - len2);

        // Check digit in first string
        if (Character.isDigit(c1)) {
            // Check digit only in first string
            if (!Character.isDigit(c2))
                return ((i > 0) && Character.isDigit(s1.charAt(i - 1)) ? 1 : c1 - c2);

            // Scan all integer digits
            int x1, x2;
            for (x1 = i + 1; (x1 < len1) && Character.isDigit(s1.charAt(x1)); x1++) ;
            for (x2 = i + 1; (x2 < len2) && Character.isDigit(s2.charAt(x2)); x2++) ;

            // Longer integer wins, first digit otherwise
            return (x2 == x1 ? c1 - c2 : x1 - x2);
        }

        // Check digit only in second string
        if (Character.isDigit(c2))
            return ((i > 0) && Character.isDigit(s2.charAt(i - 1)) ? -1 : c1 - c2);

        // No digits
        return (c1 - c2);
    }


}