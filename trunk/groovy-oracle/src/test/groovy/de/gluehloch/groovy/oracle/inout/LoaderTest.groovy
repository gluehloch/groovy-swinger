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

import static de.gluehloch.groovy.oracle.inout.Assertion.assertRowEquals
import static de.gluehloch.groovy.oracle.inout.Data.createData

import groovy.sql.Sql

import org.junit.Test
import org.junit.AfterClassimport org.junit.BeforeClass
import de.gluehloch.groovy.oracle.meta.TestDatabaseUtility
/**
 * Test of class Data.
 */
class LoaderTest {

    static Sql sql

    @Test
    void testDataBuilder() {
        def data = createData('tableName', {
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

    @Test
	void testDataInsertStatement() {
    	def data = createData('testtablename', {
    		[
    		    [col_1: 'value_col11', col_2: 'value_col12', col_3:  10.2],
    		    [col_1: 'value_col21', col_2: 'value_col22', col_3: 123.5],
    		]
        })

    	def loader = new Loader()
    	loader.load(sql ,data)
        sql.commit()

        assertRowEquals(sql, data, "select * from testtablename order by col_1")
    }

    @BeforeClass
    static void setUp() {
    	sql = TestDatabaseUtility.createConnection()
    	sql.execute('''create table testtablename(
                          col_1 varchar2(20)
                         ,col_2 varchar2(20)
                         ,col_3 number(10,2)
                       )''')
    }

    @AfterClass
    static void tearDown() {
    	sql.execute('drop table testtablename')
    	sql?.close()
        sql = null
    }

}
