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

package de.gluehloch.groovy.oracle.inout

/**
 * Compares expectations and database reality.
 */
class Assertion {

	/**
	 * Compares a database result set with an expectation. Throwss an assertion
	 * exception if necessary.
	 *
	 * @param sql A Groovy sql object.
	 * @param expectation A Data object.
	 * @param query A sql query.
	 */
    static def assertRowEquals(sql, expectation, query) {
	    def index = 0
	    sql.eachRow(query) { row ->
	        expectation.rows[index++].each() {
	            assert it.value == row[it.key]
	        }
	    }
	}

}
