package de.gluehloch.sandbox.groovy.oracle

/**
 * Informationen �ber den Prim�rschl�ssel in einer Oracle-Datenbank.
 */
class PrimaryKey {

    String name;
    def columnNames = [];

    /**
     * Liefert eine kommaseparierte Aufzählung aller Spalten.
     * Z.B: attr1, attr2, ..., attrN
     */
    def toColumnList() {
        columnNames.join(", ")
    }

    /**
     * Kopiert die Eigenschaften von diesem Objekt in ein neue erstelltes
     * Objekt 'PrimaryKey'
     *
     * @return Ein geclonter PrimaryKey.
     */
    def copy() {
        def primaryKey = new PrimaryKey(name: name)

        def columnNames2 = []
        columnNames.each { columnNames2 << it }

        primaryKey.columnNames = columnNames2

        return primaryKey
    }

    boolean equals(def object) {
        if (!(object instanceof PrimaryKey)) {
            return false
        }

        def result = true
        result = result && (name == object.name)
        result = result && (columnNames == object.columnNames)

        return result
    }

}
