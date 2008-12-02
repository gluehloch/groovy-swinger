package de.gluehloch.groovy.oracle.meta

import org.junit.Test

class ForeignKeyTest {

	@Test
    void testForeignKeyCopy() {
        def fk = new ForeignKey(name: "FOREIGN_KEY",
                rConstraintName: "CONSTRAINT_NAME",
                referencedTableName: "REFERENCED_TABLE_NAME",
                columnNames: ["COL1", "COL2", "COL3", "COL4"],
                referencedColumnNames: ["FK_COL1", "FK_COL2"])

        def cloneFk = fk.copy()

        assert cloneFk.columnNames == ["COL1", "COL2", "COL3", "COL4"]
        assert cloneFk.referencedColumnNames == ["FK_COL1", "FK_COL2"]
        assert cloneFk.name == "FOREIGN_KEY"
        assert cloneFk.rConstraintName == "CONSTRAINT_NAME"
        assert cloneFk.referencedTableName == "REFERENCED_TABLE_NAME"

        assert cloneFk == fk
        assert ["A", "B"] == ["A", "B"]
        assert ["A", "B"] != ["B", "A"]
        assert fk != new PrimaryKey(name: "Winkler")
    }

}
