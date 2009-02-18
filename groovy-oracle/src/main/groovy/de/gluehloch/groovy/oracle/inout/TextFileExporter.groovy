/*
 * $Id: Assertion.groovy 88 2009-02-10 20:09:37Z andre.winkler@web.de $
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
 * Exports database data to a flat text file.
 * 
 * @author  $Author$
 * @version $Revision$ $Date$
 */
class TextFileExporter {

	def columnSeperator = '|'

	def lineSeperator = System.getProperty('line.separator')

	/**
	 * table = [
	 *     new Data(tableName: 'tableName', rows: [
	 *         [col_1: 'value_1', col_2: 'value_2'],
	 *         [col_1: 'value_3', col_2: 'value_4'],
	 *         [col_1: 'value_5', col_2: 'value_6']
	 *     ])
	 * ]
	 */
    def export(filename, data) {
        def fw = new FileWriter(filename, true)
        try {
        	fw.write("### ${data.tableName} ###")
        	fw.write(lineSeperator)
            data.rows.each() { row ->
                def text = ""
                def length = row.values().size()
                row.values().eachWithIndex() { value, index ->
                    if (index >= length - 1) {
                        text += (value != null) ? value : ""
                    } else {
                	    text += (value != null) ? "${value}${columnSeperator}" : columnSeperator
                    }
                }
                //def text = row.values().join(columnSeperator)
                fw.write(text)
                fw.write(lineSeperator)
            }
        } finally {
        	fw.close()
        }
	}

	/**
	 * Transforms a single data row to a string.
	 *
	 * @param row A data row. Something like
	 *     <code>[col_1: 'value_1', col_2: 'value_2']</code> becomes to
	 *     <code>value_1|value_2</code>
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
        return "${text}${lineSeperator}"
	}

}
