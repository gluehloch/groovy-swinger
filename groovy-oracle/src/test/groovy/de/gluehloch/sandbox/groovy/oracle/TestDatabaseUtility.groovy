package de.gluehloch.sandbox.groovy.oracle

import groovy.sql.Sql

import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test

class TestDatabaseUtility extends GroovyTestCase {

    def sql

    /**
     * Liefert ein Groovy SQL Objekt.
     *
     * @return Ein Groovy SQL Objekt.
     */
    static def createConnection() {
        def user = System.getProperty('groovy.oracle.test.user')
        def pwd = System.getProperty('groovy.oracle.test.password')
        def url = System.getProperty('groovy.oracle.test.url')

        if (!user || !pwd || !url) {
            println """
Check your maven .settings.xml or update your system properties: 
  <profiles>
    <profile>
      <id>default</id>
      <properties>
        <compiler.encoding>UTF-8</compiler.encoding>
        <groovy.oracle.test.user>user</groovy.oracle.test.user>
        <groovy.oracle.test.password>password</groovy.oracle.test.password>
        <groovy.oracle.test.url>host:port:sid</groovy.oracle.test.url>
        <groovy.oracle.purge_recyclebin>true</groovy.oracle.purge_recyclebin>
      </properties>
    </profile>
  </profiles>
"""
            throw new IllegalStateException()
        }
    
        return OraUtils.createSql(user, pwd, url)
    }

    @Test
    void testSomething() {
    	// empty test method
    }

    @BeforeClass
    void setUp() {
    	sql = createConnection()
    	new PrepareUnitTestDatabase(sql: sql).setUp()
    }

    @AfterClass
    void tearDown() {
    	new PrepareUnitTestDatabase(sql: sql).cleanUp()
        sql?.close()
    }

}
