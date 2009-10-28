/*
 * $Id$
 * ============================================================================
 * Project groovy-swinger
 * Copyright (c) 2008-2009 by Andre Winkler. All rights reserved.
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

package de.gluehloch.sandbox.groovy.bean;

import static org.junit.Assert.assertEquals;

import javax.swing.JTextField;

import org.junit.Test;

import com.jgoodies.binding.adapter.Bindings;
import com.jgoodies.binding.value.ValueModel;

/**
 * Testet die Anbindung unter Java der Groovy Klasse <code>Person</code> in
 * Zusammenarbeit mit <code>GroovyPropertyChangeSupportBuilder</code> und
 * <code>GroovyPresentationModel</code>.
 *
 * @author  $Author$
 * @version $Revision$ $Date$
 */
public class PresentationModelTest {

	@Test
	public void testGroovierPresentationModel() {
		Person person = new Person();
		person.setAge(40);
		person.setName("Winkler");

		// Das Bean um PropertyChangeListener Eigenschaften anreichern.
		GroovyPropertyChangeSupportBuilder.preparePCLMechanics(person);

		// Ein PresentationModel ähnlich zu JGoodies.
		GroovyPresentationModel gpm = new GroovyPresentationModel(person);

		ValueModel vm = gpm.getModel("name");
		assertEquals(vm, gpm.getModel("name"));
		assertEquals("Winkler", vm.getValue());

		// ValueModel -> Bean
		vm.setValue("Hallo");
		assertEquals(vm.getValue(), person.getName());

		// Bean -> ValueModel
		person.setProperty("name", "Winkler-the-new");
		assertEquals("Winkler-the-new", vm.getValue());
		assertEquals("Winkler-the-new", gpm.getModel("name").getValue());
		
		person.setName("Was_anderes");
		assertEquals("Was_anderes", person.getName());
		// Das ValueModel hält noch den alten Wert.
		assertEquals("Winkler-the-new", vm.getValue());
		assertEquals("Winkler-the-new", gpm.getModel("name").getValue());
		person.setProperty("name", "Winkler-the-new");

        JTextField textField = new JTextField();
        Bindings.bind(textField, vm);
        assertEquals(person.getName(), textField.getText());
        textField.setText("Hamburg");
        assertEquals(person.getName(), "Hamburg");
	}

}
