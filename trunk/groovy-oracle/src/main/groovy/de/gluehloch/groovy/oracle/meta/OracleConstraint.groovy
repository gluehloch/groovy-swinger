package de.gluehloch.groovy.oracle.meta

import org.apache.commons.lang.StringUtils;

/**
 * Verwaltet ein Oracle Constraint. Ein Constraint setzt sich zusammen aus
 * einem Primary-Key und einer Liste von Foreign-Keys. Alle generierten
 * 'ALTER TABLE...' Befehle werden ohne abschließendes Semikolon generiert.
 */
class OracleConstraint {

    String tableName;

    def primaryKey;
    def foreignKeys = [];

    /**
     * Kopiert die Eigenschaften von diesem Objekt in ein neu erstelltes
     * Objekt 'OracleConstraint'
     *
     * @return Ein geclonter OracleConstraint.
     */
    def copy() {
    	def copyOfprimaryKey = (primaryKey == null) ? null : primaryKey.copy()

        def clone = new OracleConstraint(tableName: tableName,
        		primaryKey: copyOfprimaryKey)

        foreignKeys.each {
        	clone.foreignKeys << it.copy()
        }

        return clone
    }

    /**
     * Liefert ein 'ALTER TABLE...' Befehl zur Anlage eines Primary-Keys.
     *
     * @return Ein ALTER TABLE Befehl ohne ';' am Ende. Falls kein PRIMARY KEY
     *     definiert ist, liefert diese Methode <code>null</code> zurück.
     */
    def toScriptPrimaryKey() {
        def snippet
        if (primaryKey?.columnNames?.size() > 0) {
            snippet = "ALTER TABLE ${tableName} ADD (CONSTRAINT ${primaryKey.name} PRIMARY KEY ("
            snippet += primaryKey.columnNames.join(",")
            snippet += "))"
        }
        return snippet;
    }

    /**
     * Liefert 'ALTER TABLE...' Befehle zur Anlage eines Foreign-Keys.
     *
     * @return Eine Liste ALTER TABLE Befehl ohne ';' am Ende. Falls kein
     *     FOREIGN KEY definiert ist, liefert die Methode ein <code>[]</code>
     *     zurück.
     */
    def toScriptForeignKey() {
        def snippets = []
        foreignKeys.each { foreignKey ->
            def snippet = "ALTER TABLE ${tableName} ADD (CONSTRAINT ${foreignKey.name} FOREIGN KEY("
            snippet += foreignKey.columnNames.join(",")
            snippet += ") REFERENCES ${foreignKey.referencedTableName}("
            snippet += foreignKey.referencedColumnNames.join(",")
            snippet += "))"
            snippets << snippet
        }
        return snippets;
    }

     boolean equals(def object) {
         if (!(object instanceof OracleConstraint)) {
             return false
         }

         def result = true
         result = result && (primaryKey == object.primaryKey)
         result = result && (foreignKeys == object.foreignKeys)

         return result
     }

}
