/*
 * $Id$
 * ============================================================================
 * Project groovy-oracle
 * Copyright (c) 2008-2009 by Andre Winkler. All rights reserved.
 * ============================================================================
 *          GNU LESSER GENERAL PUBLIC LICENSE
 *  TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */

package de.gluehloch.groovy.oracle.inout;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * Utility class.
 * 
 * @author $Author$
 * @version $Revision$ $Date$
 */
public class InOutUtils {

	/**
	 * Splits a text String into pieces. Example:
	 * 
	 * <pre>
	 * String[] expected = new String[] { &quot;&quot;, &quot;a&quot;, &quot;b&quot;, &quot;c&quot;, &quot;&quot; };
	 * assertEquals(expected, InOutUtils.split(&quot;|a|b|c|&quot;, &quot;|&quot;));
	 * </pre>
	 * 
	 * @param _text
	 *            The text for splitting.
	 * @param _seperator
	 *            The seperator.
	 * @return The splitted string.
	 */
	public static String[] split(final String _text, final String _seperator) {
		return StringUtils.splitPreserveAllTokens(_text, _seperator);
	}

	/**
	 * Transforms a text into a data map. Example:
	 * 
	 * <pre>
	 * String text = &quot;v1|v2|v3&quot;;
	 * List&lt;String&gt; columns = InOutUtils.arrayList(&quot;col_a&quot;, &quot;col_b&quot;, &quot;col_c&quot;);
	 * assert mapping(text, columns) == [c1: 'v1', c2: 'v2', c3: 'v3'];
	 * </pre>
	 * 
	 * It is possible to return <code>null</code>, if the text parameters starts
	 * as a comment line.
	 * 
	 * @param _text
	 *            An input text.
	 * @param _columns
	 *            The column names as a key for the values.
	 * @return A data map.
	 */
	public static Map<String, String> mapping(final String _text,
			final List<String> _columns, final String _seperator) {

		if (_text.trim().startsWith("#")) {
			return null;
		}

		Map<String, String> keyValues = new LinkedHashMap<String, String>();
		String[] values = split(_text, _seperator);

		if (_columns.size() != values.length) {
			throw new IllegalArgumentException(
					"Number of tokens is not equal to number of columns.");
		}

		for (int i = 0; i < _columns.size(); i++) {
			keyValues.put(_columns.get(i), values[i]);
		}

		return keyValues;
	}

}
