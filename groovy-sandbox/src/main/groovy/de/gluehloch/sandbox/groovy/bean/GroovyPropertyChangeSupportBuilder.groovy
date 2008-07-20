package de.gluehloch.sandbox.groovy.bean

import java.beans.PropertyChangeEvent
import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport

class GroovyPropertyChangeSupportBuilder {

    static def preparePCLMechanics(def objectToPimp) {
        def support = new GroovyPropertyChangeSupport()
        objectToPimp.metaClass.propertyChangeSupport = support

        objectToPimp.metaClass.addPropertyChangeListener << { String key, PropertyChangeListener listener ->
            support.addPropertyChangeListener(key, listener)
        }
        objectToPimp.metaClass.addPropertyChangeListener << { PropertyChangeListener listener ->
            support.addPropertyChangeListener(listener)
        }

        objectToPimp.metaClass.removePropertyChangeListener << { PropertyChangeListener listener ->
            support.removePropertyChangeListener(listener)
        }

        objectToPimp.metaClass.setProperty = { String key, value ->
            def metaProperty = clazzToPimp.metaClass.getMetaProperty(key);
            def oldValue = delegate.getProperty(key);
            metaProperty.setProperty(delegate, value);
            support.firePropertyChange(delegate, key, oldValue, value)
            // delegate.propertyChangeSupport.firePropertyChange(key, oldValue, value);
        }
    }

}
