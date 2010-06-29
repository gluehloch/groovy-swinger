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

package de.awtools.groovy.swinger.bean;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import javax.swing.JComboBox;

import org.junit.Test;

import ca.odell.glazedlists.swing.EventListModel;

import com.jgoodies.binding.adapter.ComboBoxAdapter;
import com.jgoodies.binding.list.SelectionInList;

import de.awtools.groovy.swinger.bean.SelectionInListModel;
import de.gluehloch.sandbox.groovy.bean.GroovyPresentationModel;
import de.gluehloch.sandbox.groovy.bean.GroovyPropertyChangeSupportBuilder;

/**
 * Testet die JGoodies Klasse {@link SelectionInList} im Zusammenspiel mit
 * {@link GroovyPresentationModel}.
 *
 * @author  $Author$
 * @version $Revision$ $Date$
 */
public class SelectionInListTest {

    @SuppressWarnings("unchecked")
    @Test
    public void testSelectionInListWithGroovyPresentationModel() {
        final String[] fruits = new String[] { "apfel", "birne", "pflaume"};
        SelectionInListModel silm = new SelectionInListModel();
        silm.getSelectionList().clear();
        silm.getSelectionList().addAll(Arrays.asList(fruits));
        silm.setSelectedItem(fruits[0]);

        GroovyPropertyChangeSupportBuilder.preparePCLMechanics(silm);
        GroovyPresentationModel gpm = new GroovyPresentationModel(silm);        

        EventListModel elm = new EventListModel(silm.getSelectionList());
        SelectionInList sil = new SelectionInList(elm, gpm.getModel("selectedItem"));
        JComboBox combobox = new JComboBox(new ComboBoxAdapter(sil));

        assertEquals(0, combobox.getSelectedIndex());
        assertEquals("apfel", combobox.getSelectedItem());

        silm.setProperty("selectedItem", "birne");
        assertEquals(1, combobox.getSelectedIndex());
        assertEquals("birne", combobox.getSelectedItem());
    }

}
