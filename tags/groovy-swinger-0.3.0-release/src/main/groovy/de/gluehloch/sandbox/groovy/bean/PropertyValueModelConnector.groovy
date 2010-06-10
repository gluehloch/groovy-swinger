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

import java.beans.PropertyChangeListener

/**
 * Verbindet eine Bean-Eigenschaft und ein ValueModel miteinander. D.h.
 * Änderungen an der Bean-Eigenschaft übertragen sich auf das ValueModel
 * und umgekehrt.
 *
 * @author  $Author$
 * @version $Revision$ $Date$
 */
class PropertyValueModelConnector {

	def valueModel
	def bean
	def propertyName

	private def valueListener
	private def propertyListener

	void connect() {
        propertyListener = { event ->
            valueModel.setValue(event.newValue)
        } as PropertyChangeListener
        bean.addPropertyChangeListener(propertyName, propertyListener)

        valueModel.setValue(bean.@"${propertyName}")

        valueListener = { event ->
            if (event.newValue != bean."${propertyName}") {
	            bean.setProperty(propertyName, event.newValue)
            }
        } as PropertyChangeListener
        valueModel.addValueChangeListener(valueListener)
	}

	void disconnect() {
		valueModel.removeValueChangeListener(valueListener)
		bean.removePropertyChangeListener(propertyListener)
		valueListener = null
		propertyListener = null
	}

}
