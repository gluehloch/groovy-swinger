/*
* $Id$
* ============================================================================
* Project groovy-swinger
* Copyright (c) 2008-2011 by Andre Winkler. All rights reserved.
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

package de.awtools.groovy.swinger.beanbinding

import java.beans.PropertyChangeListener

import de.awtools.groovy.swinger.bean.GroovyPresentationModel

import org.junit.Test

/**
 * Test of the 1.7 annotation 'Bindable'.
 *
 * @author  $Author$
 * @version $Revision$ $Date$
 */
class AddressBindingTest {

	@Test
	void testAddressBinding() {
		def address = new Address()
		address.street = 'Beisenstr. 87'

		def pcl = { event ->
			println "New: ${event.newValue} | Old: ${event.oldValue}"
		} as PropertyChangeListener

		address.addPropertyChangeListener(pcl)

		address.street = 'Mensingstr. 29a'
	}

	@Test
	void testAddressBindingWithGroovyPresentationModel() {
		def address = new Address()
		def gpm = new GroovyPresentationModel(address)
		def vm = gpm.getModel("street")
		address.setStreet("Reeseberg")
		assert vm.getValue() == "Reeseberg"
	}

}
