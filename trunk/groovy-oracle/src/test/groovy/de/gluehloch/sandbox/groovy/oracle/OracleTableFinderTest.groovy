package de.gluehloch.sandbox.groovy.oracle

import org.junit.Test
class OracleTableFinderTest extends TestDatabaseUtility {

	@Test
	public void testOracleTableFinder() {
		OracleTableFinder otf = new OracleTableFinder()
		def tables = otf.getTables(sql)
		assert otf.getTables(sql).contains('XXX_TEST_RUN')
		assert otf.getTables(sql).contains('XXX_HIERARCHIE')
		assert otf.getTables(sql).contains('XXX_KUNDE')
	}

}
