package de.gluehloch.sandbox.groovy.bean

import org.junit.runner.JUnitCore

class StartTestSuite {

	static void main(String[] args) {
		JUnitCore.main(
				GroovyPresentationModelTest.name,
				GroovyPropertyChangeSupportBuilderTest.name,
				GroovyPropertyChangeSupportTest.name,
				JGoodiesDataBindingTest.name,
				PersonTest.name)
	}

}