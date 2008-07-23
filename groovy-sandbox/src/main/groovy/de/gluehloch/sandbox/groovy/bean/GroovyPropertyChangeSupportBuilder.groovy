package de.gluehloch.sandbox.groovy.bean

import java.beans.PropertyChangeEvent
import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport

/**
 * Ergänzt ein Objekt um PropertyChangeSupport Eigenschaften.
 */
class GroovyPropertyChangeSupportBuilder {

    static def preparePCLMechanics(clazzToPimp) {
    	// http://groovy.codehaus.org/Per-Instance+MetaClass
        def support = new GroovyPropertyChangeSupport(wrappedObject: null)
        clazzToPimp.metaClass.propertyChangeSupport << support

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
            support.firePropertyChange(delegate, key, oldValue, value)
            // delegate.propertyChangeSupport.firePropertyChange(key, oldValue, value);
        }
    }

}
