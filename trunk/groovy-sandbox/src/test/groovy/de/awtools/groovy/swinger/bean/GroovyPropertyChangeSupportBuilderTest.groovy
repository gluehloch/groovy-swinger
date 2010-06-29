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

package de.awtools.groovy.swinger.bean

import java.beans.PropertyChangeListener

import org.junit.Assert
import org.junit.Before
import org.junit.Test

/**
 * Testet die Klasse GroovyPropertyChangeSupportBuilder.
 *
 * @author  $Author$
 * @version $Revision$ $Date$
 */
class GroovyPropertyChangeSupportBuilderTest {

	def expectations = []
	def index

    private void check(event) {
        def expected = expectations[index++]
        assert expected.propertyName == event.propertyName
        assert expected.oldValue == event.oldValue, "Failed for ${event.propertyName} at ${index}: ${expected.oldValue} != ${event.oldValue}"
        assert expected.newValue == event.newValue, "Failed for ${event.propertyName} at ${index}: ${expected.newValue} != ${event.newValue}"
    }

	@Test
	void testGroovyPropertyChangeSupportBuilder() {
		def person = new Person()
		person = GroovyPropertyChangeSupportBuilder.preparePCLMechanics(person)

		def pcl = { event ->
			check event
		} as PropertyChangeListener

		def pclForAge = { event ->
			check event
		} as PropertyChangeListener

		person.addPropertyChangeListener(pcl)
		person.addPropertyChangeListener('age', pclForAge)

		person.name = 'Winkler'
		person.name = 'andre'
		person.name = 'christine'
		person.age = 24

		person.addPropertyChangeListener({ event ->
			assert event.newValue == 'newValue'
			assert event.propertyName == 'name'
		})
		person.name = 'newValue'
	}

    @Before
    void setUp() {
        expectations = []
        expectations << [propertyName: 'name', oldValue: null, newValue: 'Winkler']
        expectations << [propertyName: 'name', oldValue: 'Winkler', newValue: 'andre']
        expectations << [propertyName: 'name', oldValue: 'andre', newValue: 'christine']
        expectations << [propertyName: 'age', oldValue: null, newValue: 24]
        expectations << [propertyName: 'age', oldValue: null, newValue: 24]
        expectations << [propertyName: 'name', oldValue: 'christine', newValue: 'newValue']
        index = 0
    }

}
