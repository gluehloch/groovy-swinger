package de.gluehloch.sandbox.groovy.bean

import java.beans.PropertyChangeEvent
import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport

/**
 * Ergänzt ein Objekt um PropertyChangeSupport Eigenschaften.
 */
class GroovyPropertyChangeSupportBuilder {

    static def preparePCLMechanics(objectToPimp) {
    	def emc = new ExpandoMetaClass( objectToPimp.class, false )

    	// http://groovy.codehaus.org/Per-Instance+MetaClass
        def support = new GroovyPropertyChangeSupport(wrappedObject: objectToPimp)
    	emc.propertyChangeSupport << support

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
            support.firePropertyChangeEvent(key, oldValue, value)
            // delegate.propertyChangeSupport.firePropertyChange(key, oldValue, value);
        }
        emc.initialize()
        objectToPimp.metaClass = emc
        return objectToPimp
    }

}
