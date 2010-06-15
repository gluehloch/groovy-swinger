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

package de.gluehloch.groovy.swinger.beanbinding

import java.beans.PropertyChangeListener

import org.junit.Test

/**
 * Test of the 1.7 annotation 'Bindable'.
 *
 * @author  $Author: andre.winkler@web.de $
 * @version $Revision: 156 $ $Date: 2009-11-10 16:53:29 +0100 (Di, 10 Nov 2009) $
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

}
