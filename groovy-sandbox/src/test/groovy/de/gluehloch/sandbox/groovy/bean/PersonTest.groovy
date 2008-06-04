package de.gluehloch.sandbox.groovy.bean

import org.junit.Test
import org.junit.runner.JUnitCore

/**
 * Testet die Klasse Person.
 */
class PersonTestStub extends GroovyTestCase {

    @Test
	void testPerson() {
		Person person = new Person(name: 'Winkler',
		        surname: 'Andre', age: 37, gender: 'male')
		assert person.surname == 'Andre'
		assert person.name == 'Winkler'
		assert person.age == 37
		assert person.gender == 'male'
	}

}
