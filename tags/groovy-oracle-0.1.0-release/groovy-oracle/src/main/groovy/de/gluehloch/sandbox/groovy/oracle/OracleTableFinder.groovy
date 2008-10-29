package de.gluehloch.sandbox.groovy.oracle

/**
 * Ermittelt die User-Tabellen eines Schemas.
 */
class OracleTableFinder {

    /**
     * Liefert eine Liste der Namen aller Tabellen in einem Schema.
     *
     * @param sql Ein Groovy SQL
     * @return Eine Liste von OracleTables.
     */
    def getTables(def sql) {
        def tables = []
        sql.eachRow("select * from user_tables order by table_name") {
            tables << it.table_name;
        }
        return tables
    }

}
