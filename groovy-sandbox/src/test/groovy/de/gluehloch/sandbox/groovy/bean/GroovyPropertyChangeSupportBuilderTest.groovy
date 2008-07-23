package de.gluehloch.sandbox.groovy.bean

import java.beans.PropertyChangeListener

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GroovyPropertyChangeSupportBuilderTest {

	@Test
	void testGroovyPropertyChangeSupportBuilder() {
		def person = new Person()
		person = GroovyPropertyChangeSupportBuilder.preparePCLMechanics(person)

		def pcl = { event -> println "Ok" } as PropertyChangeListener
		person.addPropertyChangeListener(pcl)
		person.name = 'Winkler'
	}

}
