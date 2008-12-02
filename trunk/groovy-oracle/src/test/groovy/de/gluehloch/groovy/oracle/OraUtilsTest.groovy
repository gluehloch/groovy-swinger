package de.gluehloch.groovy.oracle

import org.junit.Test

/**
 * Testet die Klasse OraUtils.
 */
class OraUtilsTest {

	@Test
	void testOraUtils() {
		def user = System.getProperty('groovy.oracle.test.user')
		def pwd = System.getProperty('groovy.oracle.test.password')
		def url = System.getProperty('groovy.oracle.test.url')

		if (!user || !pwd || !url) {
			println """Check your .settings.xml:
  <profiles>
    <profile>
      <id>default</id>
      <properties>
        <compiler.encoding>UTF-8</compiler.encoding>
        <groovy.oracle.test.user>user</groovy.oracle.test.user>
        <groovy.oracle.test.password>password</groovy.oracle.test.password>
        <groovy.oracle.test.url>host:port:sid</groovy.oracle.test.url>
        <groovy.oracle.test.purge_recyclebin>true</groovy.oracle.test.purge_recyclebin>
      </properties>
    </profile>
  </profiles>
"""
			throw new IllegalStateException()
		}
	
		def sql = OraUtils.createSql(user, pwd, url);
		assert sql != null
	}

}
