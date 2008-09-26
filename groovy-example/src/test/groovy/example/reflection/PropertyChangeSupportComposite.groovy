package example.reflection

import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport

class PropertyChangeSupportComposite {

    def id

    private final delegate;
    private final pcs = new PropertyChangeSupport(this)

    PropertyChangeSupportComposite(def _delegate) {
        delegate = _delegate
    }

    void setProperty(String name, value) {
        if (hasLocalProperty(name)) {
            this.@"$name" = value
        } else {
            def oldValue = getProperty(name)
            delegate.setProperty(name, value)
            pcs.firePropertyChange(name, oldValue, value)
        }
    }

    def getProperty(String name) {
        if (hasLocalProperty(name)) {
            return this.@"$name"
        } else {
            return delegate.getProperty(name)
        }
    }

    void addPropertyChangeListener(String key, PropertyChangeListener listener) {    
        pcs.addPropertyChangeListener(key, listener)
    }

    void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener)
    }

    void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener)
    }

    private hasLocalProperty(name) {
        metaClass.properties.collect{ it.name }.contains(name)
    }
    
}
