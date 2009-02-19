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

import org.apache.commons.lang.StringUtils

/**
 * Exports (database) data to a flat text file.
 * 
 * @author  $Author$
 * @version $Revision$ $Date$
 */
class TextFileIO {

	def comment = '#'

	def columnSeperator = '|'

	def lineSeperator = System.getProperty('line.separator')

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
    def export(filename, data) {
        def fw = new FileWriter(filename, true)
        try {
        	fw.write("### ${data.tableName} ###")
        	fw.write(lineSeperator)
            data.rows.each { row ->
                fw.write(toText(row))
                fw.write(lineSeperator)
            }
        } finally {
        	fw?.close()
        }
	}

    def upload(filename, data) {
    	new FileInputStream(filename).eachLine { line ->
    		
    	}
    }

	/**
	 * Transforms a single data row to a string.
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

	 /**
	  * Transforms a text into a data object. Example:
	  * <pre>
	  * text = 'v1|v2|v3'
	  * columns = ['c1', 'c2', 'c3']
	  * assert toData(text, columns) == [c1: 'v1', c2: 'v2', c3: 'v3']
	  * </pre>
	  * It is possible to return <code>null</code>, if the text parmeters
	  * starts as a comment line.
	  *
	  * @param text An input text.
	  * @param columns The column names as a key for the values.
	  * @return A data map.
	  */
	 def toData(text, columns) {
		 if (text.startsWith(comment)) {
			 return null
		 }

    	 def tokens = StringUtils.splitPreserveAllTokens(text, columnSeperator)
		 if (columns.size() != tokens.size()) {
			 throw new IllegalStateException(
					 "ERROR: tokens.size() != columns.size(): Tokens=${tokens}; Columns=${columns}")
		 }

         def data = [:]
		 tokens.eachWithIndex() { value, index ->
			 data[columns[index]] = value
		 }
         return data
	 }

}
