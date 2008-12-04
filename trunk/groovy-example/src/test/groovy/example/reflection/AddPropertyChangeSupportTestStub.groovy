/*
 * $Id: DefaultComponentFactory.java 1384 2008-09-14 00:05:09Z andrewinkler $
 * ============================================================================
 * Project groovy-example
 * Copyright (c) 2008 by Andre Winkler. All rights reserved.
 * ============================================================================
 *          GNU LESSER GENERAL PUBLIC LICENSE
 *  TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */

package example.reflection

import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport

import org.junit.Test
import org.junit.runner.JUnitCore

/**
 * Ergänzt ein Bean um PropertyChangeSupport.
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
