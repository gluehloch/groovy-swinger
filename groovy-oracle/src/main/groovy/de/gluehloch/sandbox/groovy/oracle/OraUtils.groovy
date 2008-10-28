package de.gluehloch.sandbox.groovy.oracle

import java.sql.*

import groovy.sql.Sql

/**
 * Utitilits für den Umgang mit Oracle
 */
class OraUtils {

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
        sql.execute "purge recyclebin"
    }

}
