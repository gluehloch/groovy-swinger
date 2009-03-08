package de.gluehloch.sandbox.groovy.bean;

import static org.junit.Assert.assertEquals;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JTextField;

import org.junit.Test;

import com.jgoodies.binding.adapter.Bindings;
import com.jgoodies.binding.value.ValueModel;

/**
 * Testet die Anbindung unter Java der Groovy Klasse <code>Person</code> in
 * Zusammenarbeit mit <code>GroovyPropertyChangeSupportBuilder</code> und
 * <code>GroovyPresentationModel</code>. 
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
		assertEquals("expected 'Hallo'", vm.getValue(), person.getName());

		// Bean -> ValueModel
		//person.setName("Winkler-the-new");
		person.setProperty("name", "Winkler-the-new");
		assertEquals("Winkler-the-new", vm.getValue());
		assertEquals("Winkler-the-new", gpm.getModel("name").getValue());

        JTextField textField = new JTextField();
        Bindings.bind(textField, vm);
        assertEquals(person.getName(), textField.getText());
        textField.setText("Hamburg");
        assertEquals(person.getName(), "Hamburg");
	}

	private class MyPropertyChangeListener implements PropertyChangeListener {

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
		}
		
	}

}
