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

import java.beans.PropertyChangeListener

import org.junit.Test

import javax.swing.*

import com.jgoodies.binding.*
import com.jgoodies.binding.adapter.*
import com.jgoodies.binding.value.*
/**
 * Testet das Binding Framework von JGoodies und unseren
 * PropertyChangeSupportBuilder.
 *
 * @author  $Author$
 * @version $Revision$ $Date$
 */
class JGoodiesDataBindingTest {

	@Test
	void testJGoodiesDataBinding() {
        JTextField textField = new JTextField();

        Person pb = new Person(name: 'Winkler', surname: 'Andre',
            age: 36, gender: 'male');
        pb = GroovyPropertyChangeSupportBuilder.preparePCLMechanics(pb)

        def valueHolders = [:]
        def properties = ['name', 'surname', 'age', 'gender']
        //pb.metaClass.properties.each { metaProperty ->
        properties.each { metaProperty ->
        	ValueHolder vh = new ValueHolder()
        	//valueHolders[metaProperty.name] = vh
            valueHolders[metaProperty] = vh

        	def listener = { event ->
        		vh.value = event.newValue
        	} as PropertyChangeListener
        	//pb.addPropertyChangeListener(metaProperty.name, listener)
            pb.addPropertyChangeListener(metaProperty, listener)

            def valueListener = { event ->
                // pb.@"${metaProperty.name}" = event.newValue
                pb.@"${metaProperty}" = event.newValue
            } as PropertyChangeListener
            vh.addValueChangeListener(valueListener)
        }

        pb.name = 'Winkler_Neu'
        assert valueHolders['name'].value == 'Winkler_Neu'

        valueHolders['name'].value = 'Winkler_Noch_Neuer'
        assert pb.name == 'Winkler_Noch_Neuer'

        /* Das geht nicht:
        PresentationModel pm = new PresentationModel(pb);
        Bindings.bind(textField, pm.getModel('name'));
        assertEquals("Winkler", textField.getText());
        */
	}
	
}
