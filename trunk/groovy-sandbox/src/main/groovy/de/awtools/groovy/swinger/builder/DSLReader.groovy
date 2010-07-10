/*
 * $Id$
 * ============================================================================
 * Project groovy-swinger
 * Copyright (c) 2008-2010 by Andre Winkler. All rights reserved.
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

package de.awtools.groovy.swinger.builder

/**
 * Executes a view specification and creates a view.
 *
 * @author  $Author$
 * @version $Revision$ $Date$
 */
class DSLReader {

    def view
    def currentElement

    String toString() {
        println "View defintion:"
        println "    model: ${view.model}"
        println "    elements:"
        view.elements.each { key, value ->
            println "${value}"
        }
    }

    def name(value) {
        currentElement.name = value
        view.elements[currentElement.name] = currentElement
    }

    def columns(value) {
        currentElement.columns = value
    }

    def mandatory(value) {
        currentElement.mandatory = value
    }

    def editable(value) {
        currentElement.editable = value
    }
    
    def bind(value) {
        currentElement.binding = value
    }

    def methodMissing(String name, args) {
        if (name == 'bean') {
            view = new View()
            view.model = args[0]()
        } else if (name == 'textfield') {
            currentElement = new Textfield()
        }

        args.eachWithIndex { arg, idx ->
            arg()
        }
    }

    def propertyMissing(String name) {
        println "    DSLReader#missingProperty ${name}"
        "missingProperty"
    }

    def propertyMissing(String name, value) {
        println "    DSLReader#missingProperty ${name} with ${value}"
    }

}
