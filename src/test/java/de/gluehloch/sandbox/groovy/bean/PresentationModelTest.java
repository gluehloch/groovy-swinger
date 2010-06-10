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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigInteger;

import javax.swing.JTextField;

import org.junit.Before;
import org.junit.Test;

import com.jgoodies.binding.adapter.Bindings;
import com.jgoodies.binding.value.ValueHolder;
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

	private Person person;
	private GroovyPresentationModel gpm;
	private ValueModel vm;

	@Before
	public void setUp() {
		person = new Person();
		person.setAge(40);
		person.setName("Winkler");

		GroovyPropertyChangeSupportBuilder.preparePCLMechanics(person);
		gpm = new GroovyPresentationModel(person);
		vm = gpm.getModel("name");
	}

	@Test
	public void testGroovyPresentationModel_bean_to_model() {
		final String theOldValue = "the_old_value";
		final String theNewValue = "the_new_value";

		// That works fine with #setProperty("name", value)
		person.setProperty("name", theOldValue);
		assertEquals(theOldValue, vm.getValue());
		assertEquals(theOldValue, gpm.getModel("name").getValue());

		// Direct method call of the property does not work #setName(...)
		person.setName(theNewValue);
		assertEquals(theNewValue, person.getName());

		// ... but the ValueModel holds the old value!
		assertEquals(theOldValue, vm.getValue());
		assertEquals(theOldValue, gpm.getModel("name").getValue());

		person.setProperty("name", theNewValue);
		assertEquals(theNewValue, vm.getValue());
	}

	@Test
	public void testGroovyPresentationModel_model_to_bean() {
		assertEquals(vm, gpm.getModel("name"));
		assertEquals("Winkler", vm.getValue());

		vm.setValue("Hallo");
		assertEquals(vm.getValue(), person.getName());
	}

	@Test
	public void testGroovyPresentationModel_with_textfield() {
		ValueModel vm = gpm.getModel("name");

        JTextField textField = new JTextField();
        Bindings.bind(textField, vm);
        assertEquals(person.getName(), textField.getText());
        textField.setText("Hamburg");
        assertEquals(person.getName(), "Hamburg");
	}

	@Test
	public void testGroovyPresentationModel_addPropertyChangeListener() {
		final ValueHolder i = new ValueHolder(BigInteger.ONE);
		final ValueHolder expectation = new ValueHolder("Hamburg");

		PropertyChangeListener pcl = new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				assertEquals(expectation.getValue(), vm.getValue());
				assertEquals(expectation.getValue(), evt.getNewValue());
				i.setValue(((BigInteger) i.getValue()).add(BigInteger.ONE));
			}
		};

		gpm.addPropertyChangeListener("name", pcl);

		person.setName("Hamburg"); // Does not work!!!
		assertEquals(BigInteger.valueOf(1), i.getValue());

		person.setProperty("name", "Hamburg"); // This work!
        assertEquals(BigInteger.valueOf(2), i.getValue());

        expectation.setValue("Essen");
        vm.setValue("Essen");
        assertEquals("Essen", person.getName());
        assertEquals(BigInteger.valueOf(3), i.getValue());

        gpm.removePropertyChangeListener(pcl);
		person.setProperty("name", "Hamburg"); // This work!
        assertEquals(BigInteger.valueOf(3), i.getValue());
	}

	@Test
	public void testGroovyPresentationModel_addPropertyChangeListener_to_all() {
		PropertyChangeListener pcl = new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				assertEquals("Hamburg", evt.getNewValue());
				assertEquals("name", evt.getPropertyName());
			}
		};

		gpm.addPropertyChangeListener(pcl);
		person.setProperty("name", "Hamburg");
		gpm.removePropertyChangeListener(pcl);
	}

}
