/*
 * $Id$
 * ============================================================================
 * Project groovy-swinger
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

package de.gluehloch.sandbox.groovy.bean

import com.jgoodies.binding.value.*

import java.beans.PropertyChangeListener

/**
 * In Anlehnung an das JGoodies PresentationModel.
 * 
 * @author  $Author$
 * @version $Revision$ $Date$
 */
class GroovyPresentationModel {

	/** Das Bean dessen Properties bearbeitet werden */
	def bean

	/** Alle angelegten ValueHolders werden hier abgelegt. */
	def valueHolders = [:]

	/** Die Verbinder zwischen ValueModel und Bean-Eigenschaft. */
	def connectors = []

	/**
	 * Das hier übergebene Bean muss PCL Eigenschaften besitzen. Entweder
	 * auf natürliche Art und Weise oder über GroovyPropertyChangeSupportBuilder.
	 *
	 * @param _bean Das gewrappte Bean.
	 */
	GroovyPresentationModel(_bean) {
		bean = _bean
	}

	/**
	 * Liefert ein JGoodies ValueModel für eine Eigenschaft des gewrappten
	 * Beans.
	 *
	 * @param propertyName Der Name der Eigenschaft.
	 * @return Ein JGoodies ValueModel.
	 */
	ValueModel getModel(propertyName) {
		if (!bean.metaClass.properties.find { it.name == propertyName }) {
			throw new IllegalArgumentException("Unknown property ${propertyName}")
		}

		def vh = valueHolders[propertyName]
		if (!vh) {
			vh = new ValueHolder()
			valueHolders[propertyName] = vh

	        def pvmc = new PropertyValueModelConnector(
	                valueModel: vh, bean: bean, propertyName: propertyName)
	        pvmc.connect()
	        connectors << pvmc
		}

		return vh
	}
	
	def addPropertyChangeListener(listener) {
		bean.metaClass.properties.each { propertyName ->
			getModel(propertyName).addValueChangeListener(listener)	
		}
	}

    def addPropertyChangeListener(name, listener) {
    	getModel(name).addValueChangeListener(listener)
    }

    def removePropertyChangeListener(listener) {
    	valueHolders.each { key, value ->
    		value.removeValueChangeListener(listener)
    	}
    }

	def getBeanProperty(propertyName) {
		return bean.@"$propertyName"
	}

	def unbind() {
		connectors.each { pvmc ->
			pvmc.disconnect()
		}
 	}

}
