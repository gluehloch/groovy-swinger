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

import org.junit.Before
import org.junit.Test

/**
 * Das Delegate wird in ein Composite verpackt. Dieses kümmert sich um das 
 * versenden der PropertyChangeEvents.
 */
class PropertyChangeSupportCompositeTest extends GroovyTestCase {

    def callCounter

    @Test
    void testPropertyChangeSupportComposite() {
        PersonBean pb = new PersonBean();
        PropertyChangeSupportComposite composite = new PropertyChangeSupportComposite(pb);

        def x = { event ->
            callCounter++
            println("Event: ${event}, Name: ${event.getPropertyName()}, Old: ${event.getOldValue()}, New: ${event.getNewValue()}")
        } as PropertyChangeListener

        composite.addPropertyChangeListener(x)
        assertEquals 0, callCounter
        composite.name = "Hallo"
        assertEquals 1, callCounter

        composite.id = 'my.id'
        assertEquals 1, callCounter
    }

    @Before
    void setUp() {
        callCounter = 0
    }

}
