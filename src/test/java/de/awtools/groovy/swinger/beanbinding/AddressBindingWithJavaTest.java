/*
 * $Id: SelectionInListTest.java 181 2010-06-29 12:39:18Z andre.winkler@web.de $
 * ============================================================================
 * Project groovy-swinger
 * Copyright (c) 2008-2011 by Andre Winkler. All rights reserved.
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

package de.awtools.groovy.swinger.beanbinding;

import static junit.framework.Assert.*;

import org.junit.Test;

import com.jgoodies.binding.value.ValueModel;

import de.awtools.groovy.swinger.bean.GroovyPresentationModel;

/**
 * An example for successful integration of '@Bindable', the Groovy class
 * {@link GroovyPresentationModel} and JGoodies Data Binding for the Java
 * world.
 * 
 * @author  $Author: gluehloch@gmail.com $
 * @version $Revision: 231 $ $Date: 2011-08-27 22:59:44 +0200 (Sa, 27 Aug 2011) $
 */
public class AddressBindingWithJavaTest {

	@Test
	public void testAddressBindingWithJava() {
		// Groovy class with @Bindable annotation.
		Address address = new Address();
		address.setStreet("Mensingstr");

		// Create a presentation model which produces the ValueModels for
		// every property.
		GroovyPresentationModel gpm = new GroovyPresentationModel(address);
		ValueModel model = gpm.getModel("street");

		// Check the behavior...
		assertEquals("Mensingstr", model.getValue());
		address.setStreet("Beisenstr");
		assertEquals("Beisenstr", model.getValue());
	}

}
