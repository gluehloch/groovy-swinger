/*
 * $Id: DefaultComponentFactory.java 1384 2008-09-14 00:05:09Z andrewinkler $
 * ============================================================================
 * Project groovy-example
 * Copyright (c) 2008 by Andre Winkler. All rights reserved.
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
