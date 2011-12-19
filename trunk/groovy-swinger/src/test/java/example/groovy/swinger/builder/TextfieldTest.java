/*
 * $Id$
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

package example.groovy.swinger.builder;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.awtools.groovy.swinger.builder.Textfield;

/**
 * TODO: Test of class {@link Textfield}.
 *
 * @author by Andre Winkler, $LastChangedBy$
 * @version $LastChangedRevision$ $LastChangedDate$
 */
public class TextfieldTest {

    @Test
    public void testTextfield() {
        GGTextfield textfield = GGTextfield.TextfieldBuilder.textfield("name")
            .columns(20).mandatory().editable();
        assertEquals("name", textfield.getName());
    }

}