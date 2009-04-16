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

import org.junit.Test
import org.junit.Afterimport org.junit.Before
import de.gluehloch.groovy.oracle.meta.*
/**
 * Testet die Klassen SqlFileExporter und SqlFileImporter.
 * 
 * @author  $Author$
 * @version $Revision$ $Date$
 */
class SqlFileExportImportTest extends TestDatabaseUtility {

	 def sql

	 @Test
	 void testDatabaseExportImport() {
		 def ex = new SqlFileExporter(
			 sql: sql, query: 'select * from XXX_TEST_RUN', fileName: 'XXX_TEST_RUN.dat')
	     ex.export()
     
	     new SqlFileImporter(sql: sql, tableName: 'XXX_TEST_RUN_2', fileName: 'XXX_TEST_RUN.dat').load()

		 def counter = sql.firstRow("SELECT COUNT(*) as counter FROM XXX_TEST_RUN_2").counter
		 assert counter == 2
	     assert sql.firstRow("SELECT v_numeric FROM XXX_TEST_RUN_2 where id = 1").v_numeric == 123.456
	     assert sql.firstRow("SELECT v_numeric FROM XXX_TEST_RUN_2 where id = 2").v_numeric == 666.626
		 sql.commit()
	 }

	 @Before
	 void setUp() {
	     sql = TestDatabaseUtility.createConnection()
         def tableXXXTestRun = new OracleMetaDataFactory().createOracleTable(sql, 'XXX_TEST_RUN')
         def tableXXXTestRun_2 = tableXXXTestRun.copy()
         tableXXXTestRun_2.tableName = 'XXX_TEST_RUN_2'
         sql.execute(tableXXXTestRun_2.toScript().toString())
	 }

	 @After
	 void tearDown() {
	     sql.execute('DROP TABLE XXX_TEST_RUN_2')
	 }

}
