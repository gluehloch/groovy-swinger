package de.gluehloch.sandbox.groovy.bean

import java.beans.PropertyChangeListener

import org.junit.Test

import java.io.*
import javax.swing.*

import com.jgoodies.binding.*
import com.jgoodies.binding.adapter.*
import com.jgoodies.binding.value.*
/**
 * Testet das Binding Framework von JGoodies und unseren
 * PropertyChangeSupportBuilder.
 */
class JGoodiesDataBindingTest {

	@Test
	void testJGoodiesDataBinding() {
        JTextField textField = new JTextField();

        Person pb = new Person(name: 'Winkler', surname: 'Andre',
            age: 36, gender: 'male');
        pb = GroovyPropertyChangeSupportBuilder.preparePCLMechanics(pb)

        def valueHolders = [:]
        pb.metaClass.properties.each { metaProperty ->
        	ValueHolder vh = new ValueHolder()
        	valueHolders[metaProperty.name] = vh

        	def listener = { event ->
        		vh.value = event.newValue
        	} as PropertyChangeListener
        	pb.addPropertyChangeListener(metaProperty.name, listener)

            def valueListener = { event ->
                pb.@"${metaProperty.name}" = event.newValue
            } as PropertyChangeListener
            vh.addValueChangeListener(valueListener)
        }

        pb.name = 'Winkler_Neu'
        assert valueHolders['name'].value == 'Winkler_Neu'

        valueHolders['name'].value = 'Winkler_Noch_Neuer'
        assert pb.name == 'Winkler_Noch_Neuer'

        /* Das geht nicht:
        PresentationModel pm = new PresentationModel(pb);
        Bindings.bind(textField, pm.getModel('name'));
        assertEquals("Winkler", textField.getText());
        */
	}
	
}
