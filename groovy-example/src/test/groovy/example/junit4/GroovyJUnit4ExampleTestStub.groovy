package example.junit4

import org.junit.Test
import org.junit.runner.JUnitCore

class GroovyJunit4ExampleTest {

    @Test
	void testSomething() {
		assert 2 == 2;
	}

}

//
// Das neue GMaven Plugin unterstützt diese Art des Scriptings nicht mehr, da
// für alle Testklassen ein Stub generiert wird. Hier müssten zwei Stubs
// generiert werden (einmal die Klasse mit der Main Methode, und dann den Stub
// fuer GroovyJunit4ExampleTest), was aus Namensgründen nicht unterstützt wird.
//
//JUnitCore.main(GroovyJunit4ExampleTest.name)
//
