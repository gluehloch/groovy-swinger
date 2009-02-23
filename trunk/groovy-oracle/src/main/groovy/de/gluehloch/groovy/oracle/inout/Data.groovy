/*
 * $Id$
 * ============================================================================
 * Project groovy-oracle
 * Copyright (c) 2008-2009 by Andre Winkler. All rights reserved.
 * ============================================================================
 *          GNU LESSER GENERAL PUBLIC LICENSE
 *  TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */

package de.gluehloch.groovy.oracle.inout

/**
 * Defines data for a table.
 */
class Data {

    def comment = '#'
    def columnSeperator = '|'
    def lineSeperator = System.getProperty('line.separator')

	/** The name of the table where this data belongs to. */
	def tableName

	/** A list of maps. */
	def rows = []

    /**
     * Create a new Data object:
     * <pre>
     * // Define a data set...
     * def dataset = data('tableName', {
     *   [
     *     [col_1: 'value_1', col_2: 'value_2'],
     *     [col_1: 'value_3', col_2: 'value_4'],
     *     [col_1: 'value_5', col_2: 'value_6']
     *   ]
     * })
     * 
     * // Upload to the database table... 
     * new Loader().load(sql, dataset)
     * sql.commit()
     * 
     * // Make an assertion...
     * assertRowEquals(sql, data, "select * from testtablename order by col_1")
     * </pre>
     *
     * @param tableName The name of the database table.
     * @param dataset The data set.
     */
    static def createData(tableName, dataset) {
        new Data(tableName: tableName, rows: dataset())
    }

    // ------------------------------------------------------------------------

    /**
     * <pre>
     * date = [
     *     new Data(tableName: 'tableName', rows: [
     *         [col_1: 'value_1', col_2: 'value_2'],
     *         [col_1: 'value_3', col_2: 'value_4'],
     *         [col_1: 'value_5', col_2: 'value_6']
     *     ])
     * ]
     * </pre>
     */
    def export(filename) {
        def fw = new GFileWriter(filename)
        try {
            fw.writeln("### ${data.tableName} ###")
            data.rows.each { row ->
                fw.writeln(toText(row))
            }
        } finally {
            fw?.close()
        }
    }

     /**
      * Transforms a single data row into a string.
      *
      * @param row A data row. Something like
      *     <code>[col_1: 'value_1', col_2: 'value_2']</code> becomes to
      *     <code>value_1|value_2</code> string.
      * @return The data as a String.
      */
     def toText(row) {
         def text = ""
         def length = row.values().size()
         row.values().eachWithIndex() { value, index ->
             if (index >= length - 1) {
                 text += (value == null) ? "" : value
             } else {
                 text += (value == null) ? columnSeperator : "${value}${columnSeperator}"
             }
         }
         return text
     }

}
