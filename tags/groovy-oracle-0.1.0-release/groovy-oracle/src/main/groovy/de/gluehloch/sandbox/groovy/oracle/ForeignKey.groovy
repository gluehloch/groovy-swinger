package de.gluehloch.sandbox.groovy.oracle

/**
 * Informationen über einen Fremdschlüsselbeziehung in einer Oracle-Datenbank.
 */
class ForeignKey {

    String name
    String rConstraintName
    def columnNames = []
    String referencedTableName
    def referencedColumnNames = []

    /**
     * Kopiert die Eigenschaften von diesem Objekt in ein neue erstelltes
     * Objekt 'ForeignKey'
     *
     * @return Ein geclonter ForeignKey.
     */
    def copy() {
        def foreignKey = new ForeignKey(name: name,
                rConstraintName: rConstraintName,
                referencedTableName: referencedTableName)

        def columnNames2 = []
        columnNames.each { columnNames2 << it }

        def referencedColumnNames2 = []
        referencedColumnNames.each { referencedColumnNames2 << it }

        foreignKey.columnNames = columnNames2
        foreignKey.referencedColumnNames = referencedColumnNames2

        return foreignKey
    }

    boolean equals(def object) {
        if (!(object instanceof ForeignKey)) {
            return false
        }

        def result = true
        result = result && (name == object.name)
        result = result && (rConstraintName == object.rConstraintName)
        result = result && (referencedTableName == object.referencedTableName)
        result = result && (columnNames == object.columnNames) 
        result = result && (referencedColumnNames == object.referencedColumnNames)

        return result
    }

}
