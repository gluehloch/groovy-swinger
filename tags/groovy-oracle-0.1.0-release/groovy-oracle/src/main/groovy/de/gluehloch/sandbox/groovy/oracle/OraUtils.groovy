package de.gluehloch.sandbox.groovy.oracle

import java.sql.*

import groovy.sql.Sql

/**
 * Utitilits für den Umgang mit Oracle. Die Methode <code>purgeRecyclebin</code>
 * wird nur ausgeführt, wenn die System-Eigenschaft
 * <code>groovy.oracle.purge_recyclebin</code> gesetzt ist.
 */
class OraUtils {

	static def createSql(user, password, url, port, sid) {
		return createSql(user, password, "${url}:${port}:${sid}")
	}

    static def createSql(user, password, url) {
        def sql
        println "Generate connection: jdbc:oracle:thin:${user}/${password}@${url}"
    	try {
	        sql = Sql.newInstance(
	                "jdbc:oracle:thin:${user}/${password}@${url}",
	                user,
	                password,
	                "oracle.jdbc.driver.OracleDriver")
    	} catch (SQLException ex) {
    	    println ex.getMessage()
    		throw ex
    	}
    	return sql
    }

    static void purgeRecyclebin(sql) {
    	if (System.getProperty('groovy.oracle.purge_recyclebin') == 'true') {
    		sql.execute "purge recyclebin"
    	}
    }

}
