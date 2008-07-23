package de.gluehloch.sandbox.groovy.bean

import java.beans.PropertyChangeListener

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.JUnitCore

/**
 * Testet die Klasse GroovyPropertyChangeSupport.
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

        assert gpcs.listeners.size() == 3
        assert gpcs.listeners['name'].size() == 2
        assert gpcs.listeners['prop'].size() == 1

        checkExpectations()
        assert index == 4
	}

	@Before
	void setUp() {
		expectations = []
        expectations << [propertyName: 'name', odValue: 'adam', newValue: 'lars']
        expectations << [propertyName: 'name', odValue: 'lars', newValue: 'andre']
        expectations << [propertyName: 'name', odValue: 'andre', newValue: 'christine']
		expectations << [propertyName: 'prop', odValue: 'wurst', newValue: 'salami']
		index = 0
		gpcs = new GroovyPropertyChangeSupport(wrappedObject: this)
	}

}
