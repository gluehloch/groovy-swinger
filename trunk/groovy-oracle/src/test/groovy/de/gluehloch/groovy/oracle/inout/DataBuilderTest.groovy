/*
 * $Id: DefaultComponentFactory.java 1384 2008-09-14 00:05:09Z andrewinkler $
 * ============================================================================
 * Project groovy-oracle
 * Copyright (c) 2008 by Andre Winkler. All rights reserved.
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

import de.gluehloch.groovy.oracle.inout.DataBuilder;

import org.junit.Test
/**
 * Test for class DataBuilder.
 */
class DataBuilderTest {

	 @Test
	 void testDataBuilder() {
		 def builder = new DataBuilder()

	     def data = builder.data('tableName', {
		     [
		         [col_1: 'value_1', col_2: 'value_2'],
		         [col_1: 'value_3', col_2: 'value_4'],
		         [col_1: 'value_5', col_2: 'value_6']
		     ]
         })

         assert 'tableName' == data.tableName
         assert 'value_1' == data.rows[0].col_1
		 assert 'value_2' == data.rows[0].col_2
		 assert 'value_3' == data.rows[1].col_1
		 assert 'value_4' == data.rows[1].col_2
		 assert 'value_5' == data.rows[2].col_1
		 assert 'value_6' == data.rows[2].col_2
	 }

}
