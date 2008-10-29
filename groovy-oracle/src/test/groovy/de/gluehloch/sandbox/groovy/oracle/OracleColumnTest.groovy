package de.gluehloch.sandbox.groovy.oracle

import org.junit.Test

class OracleColumnTest {

	@Test
	void testOracleColumnCopy() {
        def oracleColumn = new OracleColumn(columnName: "name_of_column",
                dataType: "VARCHAR2", dataLength: 10, nullable: true,
                dataDefault: "Hallo")
        def clone = oracleColumn.copy()
        assert oracleColumn.columnName == clone.columnName
        assert oracleColumn.dataType == clone.dataType
        assert oracleColumn.dataLength == clone.dataLength
        assert oracleColumn.nullable == clone.nullable
        assert oracleColumn.dataDefault == clone.dataDefault

        assert oracleColumn == clone
	}

	@Test
    void testOracleColumnToScript() {
        def oracleColumn

        oracleColumn = new OracleColumn(columnName: "name_of_column",
                dataType: "VARCHAR2", dataLength: 10, nullable: true,
                dataDefault: "Hallo")
        assert oracleColumn.toScript() == "\"name_of_column\" VARCHAR2(10 BYTE) DEFAULT Hallo"

        oracleColumn = new OracleColumn(columnName: "name_of_column",
                dataType: "VARCHAR2", dataLength: 10, nullable: false,
                dataDefault: "Hallo")
        assert oracleColumn.toScript() == "\"name_of_column\" VARCHAR2(10 BYTE) DEFAULT Hallo NOT NULL ENABLE"

        oracleColumn = new OracleColumn(columnName: "name_of_column",
                dataType: "VARCHAR2", dataLength: 10, nullable: false)
        assert oracleColumn.toScript() == "\"name_of_column\" VARCHAR2(10 BYTE) NOT NULL ENABLE"

        oracleColumn = new OracleColumn(columnName: "name_of_column",
                dataType: "NUMBER", dataPrecision: 12, dataScale: 2,
                nullable: false)
        assert oracleColumn.toScript() == "\"name_of_column\" NUMBER(12,2) NOT NULL ENABLE"

        oracleColumn = new OracleColumn()
        try {
            oracleColumn.toScript()
            fail("Expected an IllegalArgumentException")
        } catch (IllegalArgumentException ex) {
            // Ok
        }
    }

}
