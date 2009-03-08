package de.gluehloch.sandbox.groovy.bean

import javax.swing.JTextField

import org.junit.Assert
import org.junit.After
import org.junit.Before
import org.junit.Test

import com.jgoodies.binding.adapter.Bindings;
/**
 * Testet die Klasse GroovyPresentationModel.
 */
class GroovyPresentationModelTest{

	def bean
	def gpm

    @Test
    void testGroovyPresentationModel() {
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

        def ok = false
        bean.addPropertyChangeListener { ok = true}
        bean.name = "Alles in Ordnung"
        assert ok
	}

	@Test
	void testGroovyPresentationModelWithUnknownProperty() {
		try {
			gpm.getModel('unknownProperty')
			Assert.fail()
		} catch (IllegalArgumentException ex) {
			assert ex.message == "Unknown property unknownProperty"
		}
	}

	@Test
	void testGroovyPresentationModelUnbind() {
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
	void testGroovyPresentationModelGetBeanProperty() {
		assert 'Winkler' == gpm.getBeanProperty('name')
		assert 'Andre' == gpm.getBeanProperty('surname')
		assert 38 == gpm.getBeanProperty('age')
	}

	@Test
	void testGroovyPresentationModelAddPropertyChangeListenerToBean() {
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
