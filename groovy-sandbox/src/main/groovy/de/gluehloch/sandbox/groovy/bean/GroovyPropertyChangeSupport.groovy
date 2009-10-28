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
 * Ein Groovy PropertyChangeSupport.
 *
 * @author  $Author$
 * @version $Revision$ $Date$
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
