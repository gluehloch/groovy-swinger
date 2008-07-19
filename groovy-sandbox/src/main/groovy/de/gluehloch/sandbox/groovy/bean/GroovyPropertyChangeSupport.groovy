package de.gluehloch.sandbox.groovy.bean

import java.beans.PropertyChangeEvent
import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport

/**
 * Ein Groovy PropertyChangeSupport.
 */
class GroovyPropertyChangeSupport {

	/** Der Marker für Listener, die alle Eigenschaften belauschen wollen. */
	private static final def ALL = '@ALL'

	def wrappedObject
	def listeners = [:]

	/**
	 * Hinzufügen eines Listeners, der an allen Eigenschaften interessiert ist.
	 *
	 * @param listener Ein PropertyChangeListener.
	 */
	def addPropertyChangeListener(def listener) {
		addPropertyChangeListener(ALL, listener)
	}

	/**
	 * Hinzufügen eines Listeners, der nur an einer bestimmten Eigenschaft
	 * interessiert ist.
	 *
	 * @param name Der Name der Eigenschaft.
	 * @param listener Ein PropertyChangeListener.
	 */
    def addPropertyChangeListener(def name, def listener) {
        def forAllProperties
        if (listeners.containsKey(name)) {
            forAllProperties = listeners.get(name)
        } else {
            forAllProperties = []
            listeners.put(name, forAllProperties)
        }
        forAllProperties.add(listener)
    }

	/**
	 * Entfernt einen Ein PropertyChangeListener.
	 *
	 * @param listener Der zu entfernende Listener.
	 */
	def removePropertyChangeListener(def listener) {
		removePropertyChangeListener(ALL, listener)
	}

	/**
	 * Entfernt einen Listener für eine bestimmte Eigenschaft.
	 *
	 * @param name Name der Eigenschaft.
	 * @param listener Ein PropertyChangeListener.
	 */
    def removePropertyChangeListener(def name, def listener) {
    	listeners.get(name)?.remove(listener)
    }

	/**
	 * Feuert ein PropertyChangeEvent.
	 *
	 * @param name Name der Eigenschaft.
	 * @param oldValue Der alte Wert.
	 * @param newValue Der neue Wert.
	 */
	protected void firePropertyChangeEvent(def name, def oldValue, def newValue) {
		PropertyChangeEvent pce = new PropertyChangeEvent(name, oldValue, newValue)
		listeners.eachWithIndex { key, listener ->
			if (key == ALL || key == name) {
				listener.propertyChange(pce)
			}
		}
	}

	// ------------------------------------------------------------------------

    private def preparePropertyChangeListenerMechanics(def clazzToPimp) {
    	def dummy = new Object()
        PropertyChangeSupport support = new PropertyChangeSupport(dummy)
        clazzToPimp.metaClass.propertyChangeSupport = support

        clazzToPimp.metaClass.addPropertyChangeListener << { String key, PropertyChangeListener listener ->
            support.addPropertyChangeListener(key, listener)
        }
        clazzToPimp.metaClass.addPropertyChangeListener << { PropertyChangeListener listener ->
            support.addPropertyChangeListener(listener)
        }

        clazzToPimp.metaClass.removePropertyChangeListener << { PropertyChangeListener listener ->
            support.removePropertyChangeListener(listener)
        }

        clazzToPimp.metaClass.setProperty = { String key, value ->
            def metaProperty = clazzToPimp.metaClass.getMetaProperty(key);
            def oldValue = delegate.getProperty(key);
            metaProperty.setProperty(delegate, value);
            delegate.propertyChangeSupport.firePropertyChange(key, oldValue, value);
        }
    }

}
