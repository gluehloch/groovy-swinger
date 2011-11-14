/*
 * $Id$
 * ============================================================================
 * Project groovy-swinger
 * Copyright (c) 2008-2011 by Andre Winkler. All rights reserved.
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

package de.awtools.groovy.swinger.bean

import static org.junit.Assert.*

import javax.swing.JTextField

import org.junit.Assert
import org.junit.After
import org.junit.Before
import org.junit.Test

import com.jgoodies.binding.adapter.Bindings;

/**
 * Testet die Klasse GroovyPresentationModel.
 *
 * @author  $Author$
 * @version $Revision$ $Date$
 */
class GroovyPresentationModelTest {

	def bean
	def gpm

    @Test
    void testGroovyPresentationModel_Bind() {
		def name = gpm.getModel('name')
		name.value = 'Berlin'
		assert bean.name == 'Berlin'
		bean.name = 'Essen'
		assert name.value == 'Essen'

		def age = gpm.getModel('age')
		age.value = 47
		assert bean.age == 47
		bean.age = 48
		assert age.value == 48

        JTextField textField = new JTextField()
        Bindings.bind(textField, name)
        textField.setText('Hamburg')
        assert bean.name == 'Hamburg'

        bean.name = 'Essen'
        assert bean.name == textField.getText()

        def ok = false
        bean.addPropertyChangeListener { ok = true}
        bean.name = "Alles in Ordnung"
        assert ok
	}

	@Test
	void testGroovyPresentationModel_WithUnknownProperty() {
		try {
			gpm.getModel('unknownProperty')
			Assert.fail()
		} catch (IllegalArgumentException ex) {
			assert ex.message == "Unknown property unknownProperty"
		}
	}

	@Test
	void testGroovyPresentationModel_Unbind() {
        def name = gpm.getModel('name')
        name.value = 'Berlin'
        assert bean.name == 'Berlin'

        gpm.unbind()

        name.value = 'Zehlendorf'
        assert bean.name == 'Berlin'

        bean.name = 'Wusterkamp'
        assert name.value == 'Zehlendorf'
	}

	@Test
	void testGroovyPresentationModel_GetBeanProperty() {
		assert 'Winkler' == gpm.getBeanProperty('name')
		assert 'Andre' == gpm.getBeanProperty('surname')
		assert 38 == gpm.getBeanProperty('age')
	}

	@Test
	void testGroovyPresentationModel_AddPropertyChangeListenerToBean() {
		def value = null
		bean.addPropertyChangeListener({ event -> value = event.propertyName == 'name' ? event.newValue : null})
		bean.name = 'Hallo Andre'
		assert value == 'Hallo Andre' 
	}

	@Before
	void setUp() {
        bean = new Person(name : 'Winkler', surname : 'Andre', age : 38)
        bean = GroovyPropertyChangeSupportBuilder.preparePCLMechanics(bean)
        gpm = new GroovyPresentationModel(bean)
	}

	@After
	void tearDown() {
		
	}

}
