package de.gluehloch.sandbox.groovy.bean

import java.beans.PropertyChangeListener

import org.junit.Assert
import org.junit.Before
import org.junit.Test

/**
 * Testet die Klasse GroovyPropertyChangeSupportBuilder.
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
	}

    @Before
    void setUp() {
        expectations = []
        expectations << [propertyName: 'name', oldValue: null, newValue: 'Winkler']
        expectations << [propertyName: 'name', oldValue: 'Winkler', newValue: 'andre']
        expectations << [propertyName: 'name', oldValue: 'andre', newValue: 'christine']
        expectations << [propertyName: 'age', oldValue: null, newValue: 24]
        expectations << [propertyName: 'age', oldValue: null, newValue: 24]
        index = 0
    }

}
