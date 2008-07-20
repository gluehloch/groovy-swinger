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
			println "NAME: ${event.name}: OLD: ${event.oldValue}, NEW: ${event.newValue}"
			callCounter++
		}

		def gpcs = new GroovyPropertyChangeSupport()
		gpcs.addPropertyChangeListener(listener as PropertyChangeListener)
		gpcs.firePropertyChangeEvent(this, "name", "adam", "lars")
		assert callCounter == 1
	}

	@Before
	void setUp() {
		callCounter = 0
	}

}
