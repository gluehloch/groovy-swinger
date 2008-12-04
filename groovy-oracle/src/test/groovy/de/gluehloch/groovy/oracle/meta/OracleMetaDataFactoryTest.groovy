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

package de.gluehloch.groovy.oracle.meta

import org.junit.Test

class OracleMetaDataFactoryTest extends TestDatabaseUtility {

    @Test
    void testOracleMetaDataFactoryWithIgnores() {
        def omdf = new OracleMetaDataFactory()
        def oracleTable = omdf.createOracleTablesWithIgnore(sql, ["XXX_TEST_RUN"])

        def ot = oracleTable.find { it.tableName == 'XXX_HIERARCHIE' }
        assert ot.tableName == 'XXX_HIERARCHIE'

        ot = oracleTable.find { it.tableName == 'XXX_KUNDE' }
        assert ot.tableName == 'XXX_KUNDE'

        oracleTable = omdf.createOracleTablesWithIgnore(sql, [])
        assert oracleTable.find { it.tableName == 'XXX_TEST_RUN' }.tableName == 'XXX_TEST_RUN'
        assert oracleTable.find { it.tableName == 'XXX_HIERARCHIE' }.tableName == 'XXX_HIERARCHIE'
        assert oracleTable.find { it.tableName == 'XXX_KUNDE' }.tableName == 'XXX_KUNDE'
    }

    @Test
    void testOracleMetaDataFactory() {
        def omdf = new OracleMetaDataFactory()
        def oracleTable = omdf.createOracleTables(sql)
        assert oracleTable.find { it.tableName == 'XXX_TEST_RUN' }.tableName == 'XXX_TEST_RUN'
        assert oracleTable.find { it.tableName == 'XXX_HIERARCHIE' }.tableName == 'XXX_HIERARCHIE'
        assert oracleTable.find { it.tableName == 'XXX_KUNDE' }.tableName == 'XXX_KUNDE'

        oracleTable = omdf.createOracleTable(sql, "XXX_TEST_RUN")
        assertEquals "XXX_TEST_RUN", oracleTable.tableName
        assertEquals "PK_XXX_TEST_RUN", oracleTable.constraint.primaryKey.name
        assertEquals 0, oracleTable.constraint.foreignKeys.size()

        oracleTable = omdf.createOracleTable(sql, "XXX_HIERARCHIE")
        assertEquals "XXX_HIERARCHIE", oracleTable.tableName
        assertEquals "PK_XXX_HIERARCHIE", oracleTable.constraint.primaryKey.name
        assertEquals 1, oracleTable.constraint.foreignKeys.size()

        oracleTable = omdf.createOracleTable(sql, "XXX_KUNDE")
        assertEquals "XXX_KUNDE", oracleTable.tableName
        assertEquals "PK_XXX_KUNDE", oracleTable.constraint.primaryKey.name
        assertEquals 2, oracleTable.constraint.foreignKeys.size()

        assert oracleTable.constraint.foreignKeys.find { it.name == 'FK_XXX_KUNDE_HIERARCHIE' } != null
        // assertEquals "FK_XXX_KUNDE_HIERARCHIE", oracleTable.constraint.foreignKeys[0].name
        assert oracleTable.constraint.foreignKeys.find { it.name == 'FK_XXX_KUNDE' } != null
        // assertEquals "FK_XXX_KUNDE", oracleTable.constraint.foreignKeys[1].name
    }

    @Test
    void testOracleMetaDataFactoryNoTable() {
        def omdf = new OracleMetaDataFactory()
        try {
        	def oracleTable = omdf.createOracleTable(sql, "unknown_table")
        	fail("Exception expected!")
        } catch (IllegalArgumentException ex) {
        	// Ok
        }
    }

}
