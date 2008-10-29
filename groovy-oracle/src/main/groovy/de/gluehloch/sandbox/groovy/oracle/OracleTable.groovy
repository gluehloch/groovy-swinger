package de.gluehloch.sandbox.groovy.oracle

import org.apache.commons.lang.StringUtils

/**
 * Verwaltet die Daten einer Oracle Datenbanktabelle.
 */
class OracleTable {

	def nl = System.getProperty("line.separator");

    String tableName;

    /** Eine Liste mit OracleColumn Einträgen. */
    def columnMetaData = [];

    /** Ein OracleConstraint Objekt. */
    def constraint;

    /**
     * Kopiert die Eigenschaften von diesem Objekt in ein neu erstelltes
     * Objekt 'OracleTable'
     *
     * @return Ein geclonter OracleTable.
     */
    def copy() {
        def clone = new OracleTable(tableName: tableName,
        		constraint: constraint?.copy())

        columnMetaData.each {
        	clone.columnMetaData << it.copy()
        }
        return clone
    }

    /**
     * Liefert eine kommaseparierte Aufzählung aller Spalten.
     * Z.B: attr1, attr2, ..., attrN
     */
    def toColumnList() {
        return columnMetaData.collect { it.columnName }.join(", ")

//        def stmt = ""
//        columnMetaData.eachWithIndex { column, index ->
//            stmt += column.columnName
//            if (index < columnMetaData.size() - 1) {
//                stmt += ", "
//            }
//        }
//        return stmt
    }

    /**
     * Liefert eine kommaseparierte Aufzählung aller Spalten.
     * Z.B: attr1, attr2, ..., attrN
     *
     * @param ignoreColumns Eine Liste der zu ignorierenden Spalten.
     */
    def toColumnList(def ignoreColumns) {
        def columns = columnMetaData.collect { it.columnName }
        (columns - ignoreColumns).join(", ")
    }

    /**
     * Liefert einen 'CREATE TABLE...' Befehl zur Neuanlage dieser Oracle
     * Datenbanktabelle. Der Befehl wird ohne ';' abgeschlossen.
     *
     * @return Ein Skript zur Neuanlage einer Datenbanktabelle ohne Primary-Key
     *     und Foreign-Key Befehle.
     */
    def toScript() {
         def snippet = "CREATE TABLE \"${tableName}\"(${nl}";
         columnMetaData.eachWithIndex { column, index ->
             snippet = snippet + "\t${column.toScript()}";
             if (index + 1 < columnMetaData.size()) {
            	 snippet = snippet + ",${nl}"
             } else {
            	 snippet = snippet + "${nl})"
             }
         }
         return snippet;
     }

    boolean equals(def object) {
        if (!(object instanceof OracleTable)) {
            return false
        }

        def result = true
        result = result && (tableName == object.tableName)
        result = result && (columnMetaData == object.columnMetaData)
        result = result && (constraint == object.constraint)

        return result
    }

    String toString() {
        return toScript().toString();
    }

}