/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.markiewb.netbeans.plugins.hints.common;

/**
 *
 * @author Bender
 */
public class StringUtils {

    static boolean isEmpty(String string) {
	return null == string || "".equals(string);
    }

    public static String join(Iterable<String> list, String separator) {
	boolean first = true;
	StringBuilder sb = new StringBuilder(200);
	for (String string : list) {
	    if (isEmpty(string)) {
		continue;
	    }
	    if (!first) {
		sb.append(separator);
	    }
	    sb.append(string);
	    first = false;
	}
	return sb.toString();
    }
    
}
