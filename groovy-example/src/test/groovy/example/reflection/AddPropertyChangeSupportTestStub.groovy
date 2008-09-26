package example.reflection

import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport

import org.junit.Test
import org.junit.runner.JUnitCore

/**
 * Fügt PropertyChangeSupport in ein Java Bean ein.
 */
class AddPropertyChangeSupportTest {

    /**
     * Erweiterung einer Klasse um Methoden für das Verwalten von
     * PropertyChangeListener.
     */
    @Test
	void testAddPropertyChangeSupportToBean() {
		PersonBean personBean = new PersonBean()
	    preparePropertyChangeListenerMechanics(personBean)

		def x = { event -> println("Call_1: ${event.getPropertyName()}, ${event.getOldValue()}, ${event.getNewValue()}") } as PropertyChangeListener
		personBean.addPropertyChangeListener(x);

//		Funktioniert so leider nicht.
//	    personBean.propertyChange = { event -> println("Call_2: ${event}") } as PropertyChangeListener;
	    personBean.name = "Andre Winkler";
	}

    private def preparePropertyChangeListenerMechanics(def objectToPimp) {
        def emc = new ExpandoMetaClass( objectToPimp.class, false )

        PropertyChangeSupport support = new PropertyChangeSupport(objectToPimp)
        emc.propertyChangeSupport = support

        emc.addPropertyChangeListener << { String key, PropertyChangeListener listener ->
            support.addPropertyChangeListener(key, listener)
        }
        emc.addPropertyChangeListener << { PropertyChangeListener listener ->
            support.addPropertyChangeListener(listener)
        }

        emc.removePropertyChangeListener << { PropertyChangeListener listener ->
            support.removePropertyChangeListener(listener)
        }

        emc.setProperty = { String key, value ->
            def metaProperty = objectToPimp.metaClass.getMetaProperty(key);
            def oldValue = delegate.getProperty(key);
            metaProperty.setProperty(delegate, value);
            support.firePropertyChange(key, oldValue, value)
            //delegate.support.firePropertyChange(key, oldValue, value);
        }

        emc.initialize()
        objectToPimp.metaClass = emc
        return objectToPimp
    }

}

//JUnitCore.main(AddPropertyChangeSupportTest.name)
