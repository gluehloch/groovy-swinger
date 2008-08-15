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

	/** Das Objekt welches um PCL Eigenschaften ergänzt wird. */
	protected def wrappedObject

	/**
	 * Alle PC-Listener mit key gleich dem Namen der Eigenschaft auf die
	 * gelauscht wird.
	 */
	protected def listeners = [:]

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
        if (!forAllProperties.contains(listener)) {
        	forAllProperties << listener
        }
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
		if (name == ALL) {
			listeners.each { key, value ->
				value.remove(listener)
			}
		}    	
    }

	/**
	 * Feuert ein PropertyChangeEvent.
	 *
	 * @param name Name der Eigenschaft.
	 * @param oldValue Der alte Wert.
	 * @param newValue Der neue Wert.
	 */
	protected void firePropertyChangeEvent(def name, def oldValue, def newValue) {
		PropertyChangeEvent pce =
			new PropertyChangeEvent(wrappedObject, name, oldValue, newValue)

		listeners.each { key, values ->
			if (key == ALL || key == name) {
				values.each { l ->
					l.propertyChange(pce)
				}
			}
		}
	}

}
