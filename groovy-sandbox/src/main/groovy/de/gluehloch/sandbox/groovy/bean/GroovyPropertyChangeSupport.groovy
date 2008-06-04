package de.gluehloch.sandbox.groovy.bean

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

	def addPropertyChangeListener(def listener) {
		addPropertyChangeListener(ALL, listener)
	}

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

	def removePropertyChangeListener(def listener) {
		removePropertyChangeListener(ALL, listener)
	}

    def removePropertyChangeListener(def name, def listener) {
    	listeners.get(name)?.remove(listener)
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
