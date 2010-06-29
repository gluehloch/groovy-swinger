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
 * Testet die Klasse GroovyPropertyChangeSupport.
 *
 * @author  $Author$
 * @version $Revision$ $Date$
 */
class GroovyPropertyChangeSupportTest {

	def gpcs
	def expectations = []
	def index

	private void check(event) {
		def expected = expectations[index]
        assert expected.propertyName == event.propertyName
        assert expected.oldValue == event.oldValue
        assert expected.newValue == event.newValue
	}

	private void checkExpectations() {
        expectations.eachWithIndex { value, index ->
        gpcs.firePropertyChangeEvent(
            expectations[index].propertyName,
            expectations[index].oldValue,
            expectations[index].newValue)
        }
	}

	@Test
	void testGroovyPropertyChangeSupportWithAllOrNever() {
		def listener = { event ->
			check event
			index++
		}

		def shouldNeverBeCalled = { event ->
			Assert.fail()
		}

		
		def pcl = listener as PropertyChangeListener

		gpcs.addPropertyChangeListener(pcl)
		gpcs.addPropertyChangeListener(
			'never', shouldNeverBeCalled as PropertyChangeListener)
		assert gpcs.listeners.size() == 2
		assert gpcs.listeners['@ALL'].size() == 1
		assert gpcs.listeners['@ALL'][0] == pcl
        assert gpcs.listeners['never'].size() == 1

        checkExpectations()
		assert index == 4
	}

	@Test
	void testGroovyPropertyChangeSupportWithNamedListener() {
        def listener1 = { event ->
        	check event
        }
        def listener2 = { event ->
        	check event
        	index++
        }
        def listener3 = { event ->
        	check event
        	index++
        }

        gpcs.addPropertyChangeListener(
        	'name', listener1 as PropertyChangeListener)
        gpcs.addPropertyChangeListener(
            'name', listener2 as PropertyChangeListener)
        gpcs.addPropertyChangeListener(
            'prop', listener3 as PropertyChangeListener)

        assert gpcs.listeners.size() == 2
        assert gpcs.listeners['name'].size() == 2
        assert gpcs.listeners['prop'].size() == 1

        checkExpectations()
        assert index == 4
	}

	@Test
	void testGroovyPropertyChangeSupportInvariantListenerAddingAndRemoving() {
		def eventCounter = 0
        def listener = { event ->
        	eventCounter++
        }

        def pcl = listener as PropertyChangeListener
        gpcs.addPropertyChangeListener('name', pcl)
        gpcs.addPropertyChangeListener('name', pcl)
        assert gpcs.listeners.size() == 1
        assert gpcs.listeners['name'].size() == 1
        assert gpcs.listeners['prop'] == null

        gpcs.addPropertyChangeListener('age', pcl)
        assert gpcs.listeners.size() == 2
        assert gpcs.listeners['name'].size() == 1
        assert gpcs.listeners['age'].size() == 1
        gpcs.firePropertyChangeEvent('name', 'oldValue', 'newValue')
        assert eventCounter == 1
        gpcs.firePropertyChangeEvent('age', 12, 24)
        assert eventCounter == 2

        gpcs.removePropertyChangeListener(pcl)
        gpcs.firePropertyChangeEvent('age', 12, 24)
        assert eventCounter == 2
    }

	@Before
	void setUp() {
		expectations = []
        expectations << [propertyName: 'name', oldValue: 'adam', newValue: 'lars']
        expectations << [propertyName: 'name', oldValue: 'lars', newValue: 'andre']
        expectations << [propertyName: 'name', oldValue: 'andre', newValue: 'christine']
		expectations << [propertyName: 'prop', oldValue: 'wurst', newValue: 'salami']
		index = 0
		gpcs = new GroovyPropertyChangeSupport(wrappedObject: this)
	}

}
