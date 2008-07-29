package de.gluehloch.sandbox.groovy.bean;

import static org.junit.Assert.*;
import org.junit.Test;

import com.jgoodies.binding.value.ValueModel;

import de.gluehloch.sandbox.groovy.bean.GroovyPresentationModel;
import de.gluehloch.sandbox.groovy.bean.GroovyPropertyChangeSupportBuilder;
import de.gluehloch.sandbox.groovy.bean.Person;

/**
 * Testet die Anbindung unter Java der Groovy Klasse <code>Person</code> in
 * Zusammenarbeit mit <code>GroovyPropertyChangeSupportBuilder</code> und
 * <code>GroovyPresentationModel</code>. 
 */
public class PresentationModelTest {

	@Test
	public void testGroovy() {
		Person person = new Person();
		person.setAge(39);
		person.setName("Winkler");

		GroovyPropertyChangeSupportBuilder.preparePCLMechanics(person);
		GroovyPresentationModel gpm = new GroovyPresentationModel(person);
		Object object = gpm.getModel("name");
		ValueModel vm = (ValueModel) object;
		vm.setValue("Hallo");
		assertEquals("expected 'Hallo'", vm.getValue(), person.getName());
	}

}
