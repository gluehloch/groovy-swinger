package de.gluehloch.sandbox.groovy.bean

import org.junit.runner.JUnitCore
//JUnitCore.main(PersonTest.name)
class StartTestSuite {

	static void main(String[] args) {
		JUnitCore.main(
				PersonTest.name,
				GroovyPropertyChangeSupportTest.name,
				GroovyPropertyChangeSupportBuilderTest.name)
	}

}
