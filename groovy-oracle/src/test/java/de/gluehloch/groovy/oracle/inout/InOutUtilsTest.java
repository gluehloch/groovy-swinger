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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

/**
 * Test for class {@link InOutUtils}.
 * 
 * @author $Author$
 * @version $Revision$ $Date$
 */
public class InOutUtilsTest {

	@Test
	public void testInOutUtilsToString() {
		List<String> strings = new ArrayList<String>();
		strings.add("a");
		strings.add("b");
		strings.add("c");
		strings.add("");
		assertEquals("a|b|c|", InOutUtils.toString(strings, "|"));

		strings.clear();
		strings.add("a");
		strings.add("b");
		strings.add("c");
		strings.add("d");
		assertEquals("a|b|c|d", InOutUtils.toString(strings, "|"));

		strings.clear();
		strings.add(null);
		strings.add("b");
		strings.add("c");
		strings.add(null);
		assertEquals("|b|c|", InOutUtils.toString(strings, "|"));
	}

	@Test
	public void testInOutUtilsSplit() {
		String[] expected = new String[] { "", "a", "b", "c", "" };
		assertArrayEquals(expected, InOutUtils.split("|a|b|c|", "|"));

		expected = new String[] { "a", "b", "c" };
		assertArrayEquals(expected, InOutUtils.split("a|b|c", "|"));
	}

	@Test
	public void tesInOutUtilsMapping() {
		List<String> values = new ArrayList<String>();
		Collections.addAll(values, "col_a", "col_b", "col_c");

		Map<String, String> dataMap = InOutUtils.mapping("a|b|c", values, "|");

		Map<String, String> expected = new HashMap<String, String>();
		expected.put("col_a", "a");
		expected.put("col_b", "b");
		expected.put("col_c", "c");

		for (String key : expected.keySet()) {
			assertEquals(expected.get(key), dataMap.get(key));
		}
	}
}
