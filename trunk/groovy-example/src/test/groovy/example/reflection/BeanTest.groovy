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

package example.reflection;

class BeanTest extends GroovyTestCase {

    def callCounter;

	void testSomething() {
		PersonBean.metaClass.getProperty = { String key ->
		   def metaProperty = PersonBean.metaClass.getMetaProperty(key); 
		   def result = metaProperty.getProperty(delegate);
		   callCounter++;
		   return result;
		}

		PersonBean person = new PersonBean(name: "Winkler", firstname: "Andre",
		    age: 36, street: "Mensingstr", city: "Hamburg");

		// Funktioniert erst mit Groovy 1.1
		def metaProperty = PersonBean.metaClass.getProperty("name");

		PersonBean.metaClass.setProperty = { String key, value ->
			def metaProperty2 = PersonBean.metaClass.getMetaProperty(key);
			metaProperty2.setProperty(key, value);
			println "MetaProperty::setProperty";
		}
	
		// Hier wird getProperty() leider nicht aufgerufen.
		assert person.getName() == "Winkler";
		assert callCounter == 0;

		// Hier wird getProperty() aufgerufen.
		assert person.name == "Winkler";
		assert callCounter == 1;
		assert person.age == 36;
		assert callCounter == 2;
		assert person.toString() == "Winkler, Andre";
		assert callCounter == 2;

		println "Hallo (1)";

		person.setName("Kroschk");
		assert person.getName() == "Kroschk";

		println "Hallo (2)";
	}

	void setUp() {
	    callCounter = 0;
	}

}
