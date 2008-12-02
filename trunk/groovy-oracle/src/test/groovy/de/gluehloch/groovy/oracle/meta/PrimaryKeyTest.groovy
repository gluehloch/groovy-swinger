package de.gluehloch.groovy.oracle.meta

import org.junit.Test

class PrimaryKeyTest {

	@Test
    void testPrimaryKeyToColumnList() {
        def pk = new PrimaryKey(name: "PRIMARY_KEY",
                columnNames: ["PK1", "PK2", "PK3"])
        assert pk.toColumnList() == "PK1, PK2, PK3"
    }

	@Test
	void testPrimaryKeyCopy() {
		def pk = new PrimaryKey(name: "PRIMARY_KEY",
		        columnNames: ["PK1", "PK2", "PK3"])
		def clonePk = pk.copy()

		assert clonePk.name == "PRIMARY_KEY"
		assert clonePk.columnNames == ["PK1", "PK2", "PK3"]

		assert clonePk == pk
		assert pk != new ForeignKey(name: "FOREIGN_KEY")
	}

}
