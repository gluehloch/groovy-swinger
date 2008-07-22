package de.gluehloch.sandbox.groovy.bean

import java.beans.PropertyChangeListener

import org.junit.Before
import org.junit.Test
import org.junit.runner.JUnitCore

/**
 * Testet die Klasse GroovyPropertyChangeSupport.
 */
class GroovyPropertyChangeSupportTest {

	private def callCounter

	@Test
	void testGroovyPropertyChangeSupport() {
		def listener = { event ->
			println "NAME: ${event.propertyName}: OLD: ${event.oldValue}, NEW: ${event.newValue}"
			callCounter++
		}

		def gpcs = new GroovyPropertyChangeSupport(wrappedObject: this)
		def pcl = listener as PropertyChangeListener
		gpcs.addPropertyChangeListener(pcl)
		assert gpcs.listeners.size() == 1
		assert gpcs.listeners['@ALL'].size() == 1
		assert gpcs.listeners['@ALL'][0] == pcl

		gpcs.firePropertyChangeEvent('name', 'adam', 'lars')
		assert callCounter == 1
	}

	@Before
	void setUp() {
		callCounter = 0
	}

}
