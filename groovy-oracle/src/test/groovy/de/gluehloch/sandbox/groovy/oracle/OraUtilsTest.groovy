package de.gluehloch.sandbox.groovy.oracle

import org.junit.Test

/**
 * Testet die Klasse OraUtils.
 */
class OraUtilsTest {

	@Test
	void testOraUtils() {
		def sql = OraUtils.createSql('cb', 'cbrms', 'zora1entw.zit.commerzbank.com:1521:RE09');
		assert sql != null
	}

}
