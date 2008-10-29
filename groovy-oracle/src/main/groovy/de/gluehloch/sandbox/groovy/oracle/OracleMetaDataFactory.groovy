package de.gluehloch.sandbox.groovy.oracle

import groovy.sql.Sql

/**
 * Erstellt ein OracleTable Objekt.
 */
class OracleMetaDataFactory {

    private final OracleColumnFinder ocf = new OracleColumnFinder()

    /**
     * Liefert die Tabellenbeschreibungen für ein Schema.
     *
     * @param sql Verbindung zur Datenbank.
     */
    def createOracleTables(Sql sql) {
    	def oracleTables = createOracleTables(
    			sql, new OracleTableFinder().getTables(sql))
    	return oracleTables
    }

    /**
     * Liefert die Tabellenbeschreibungen für ein Schema.
     *
     * @param sql Verbindung zur Datenbank.
     * @param ignores Die zu ignorierenden Tabellennamen.
     */
    def createOracleTablesWithIgnore(Sql sql, List ignores) {
    	def tableNames = new OracleTableFinder().getTables(sql) - ignores
        def oracleTables = createOracleTables(sql, tableNames)
        return oracleTables
    }

    /**
     * Liefert die Tabellenbeschreibungen für eine Liste von Tabellennamen.
     *
     * @param sql Verbindung zur Datenbank.
     * @param tableNames Die Namen der Tabellen.
     */
    def createOracleTables(Sql sql, List tableNames) {
        def oracleTables = []
        tableNames.each { tableName ->
            oracleTables.add(createOracleTable(sql, tableName))
        }
        return oracleTables
    }

    /**
     * Liefert eine Tabellenbeschreibung.
     *
     * @param sql Verbindung zur Datenbank.
     * @param tableName Der Name der Tabelle.
     */
    OracleTable createOracleTable(Sql sql, String tableName) {
        def columnData = ocf.getColumns(sql, tableName)
        if (columnData == null || columnData.size() == 0) {
        	throw new IllegalArgumentException("Unknown table ${tableName}")
        }

        def constraint = ocf.getConstraint(sql, tableName)
        def ot = new OracleTable(tableName: tableName,
                columnMetaData: columnData, constraint: constraint)
        return ot
    }

}
