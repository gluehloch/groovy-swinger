/*
 * $Id$
 * ============================================================================
 * Project groovy-swinger
 * Copyright (c) 2008-2009 by Andre Winkler. All rights reserved.
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

package de.gluehloch.sandbox.groovy.bean

import java.beans.PropertyChangeEvent
import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport

/**
 * Ergänzt ein Objekt um PropertyChangeSupport Eigenschaften.
 *
 * @author  $Author$
 * @version $Revision$ $Date$
 */
class GroovyPropertyChangeSupportBuilder {

	/**
	 * Es wird einer einzelnen bestehenden Objektinstanz Eigenschaften
	 * hinzugefügt.
	 * Siehe auch http://groovy.codehaus.org/Per-Instance+MetaClass.
	 */
    static def preparePCLMechanics(objectToPimp) {
    	def emc = new ExpandoMetaClass( objectToPimp.class, false )

        def support = new GroovyPropertyChangeSupport(wrappedObject: objectToPimp)
    	emc.propertyChangeSupport << support

    	emc.addPropertyChangeListener << { String key, PropertyChangeListener listener ->
            support.addPropertyChangeListener(key, listener)
        }

    	emc.addPropertyChangeListener << { PropertyChangeListener listener ->
            support.addPropertyChangeListener(listener)
        }

        emc.addPropertyChangeListener << { Closure listener ->
            support.addPropertyChangeListener(listener as PropertyChangeListener)
        }

    	emc.removePropertyChangeListener << { PropertyChangeListener listener ->
            support.removePropertyChangeListener(listener)
        }

    	emc.setProperty = { String key, value ->
            def metaProperty = objectToPimp.metaClass.getMetaProperty(key);
            def oldValue = delegate.getProperty(key);
            metaProperty.setProperty(delegate, value);
            support.firePropertyChangeEvent(key, oldValue, value)
            //delegate.propertyChangeSupport.firePropertyChange(key, oldValue, value);
        }
        emc.initialize()
        objectToPimp.metaClass = emc
        return objectToPimp
    }

}
