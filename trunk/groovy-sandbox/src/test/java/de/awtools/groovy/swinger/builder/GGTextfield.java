/*
 * $Id$
 * ============================================================================
 * Project groovy-swinger
 * Copyright (c) 2008-2010 by Andre Winkler. All rights reserved.
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

package de.awtools.groovy.swinger.builder;

import javax.swing.JTextField;

import com.jgoodies.binding.adapter.Bindings;
import com.jgoodies.validation.view.ValidationComponentUtils;

import de.awtools.groovy.swinger.bean.GroovyPresentationModel;

/**
 * TODO: Create a Groovy.
 * 
 * Builder für Textfelder. Beispiel:
 * 
 * <pre>
 * Textfield tf1 = textfield("name").columns(10).mandatory().editable();
 * Textfield tf2 = textfield("vintage").columns(5).nonEditable();
 * </pre>
 * 
 * @author by Andre Winkler, $LastChangedBy$
 * @version $LastChangedRevision$ $LastChangedDate$
 */
public class GGTextfield {

    public static class TextfieldBuilder {
        private final String name;

        private int columns;

        private boolean mandatory = false;

        private boolean editable = true;

        private TextfieldBuilder(final String _name) {
            name = _name;
        }

        public static TextfieldBuilder textfield(final String _name) {
            return new TextfieldBuilder(_name);
        }

        public TextfieldBuilder columns(final int _columns) {
            columns = _columns;
            return this;
        }

        public TextfieldBuilder mandatory() {
            mandatory = true;
            return this;
        }

        public GGTextfield editable() {
            return new GGTextfield(name, columns, mandatory, editable);
        }

        public GGTextfield nonEditable() {
            editable = false;
            return new GGTextfield(name, columns, mandatory, editable);
        }
    }

    // ------------------------------------------------------------------------

    private String name;

    private boolean mandatory = false;

    private int columns;

    private boolean editable = true;

    private GGTextfield(final String _name, final int _columns,
            final boolean _mandatory, final boolean _editable) {

        name = _name;
        columns = _columns;
        mandatory = _mandatory;
        editable = _editable;
    }

    /* (non-Javadoc)
     * @see de.winkler.betoffice.swing.uc.common.ComponentDescription#getName()
     */
    public String getName() {
        return name;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public int getColumns() {
        return columns;
    }

    public boolean isEditable() {
        return editable;
    }

    public JTextField createComponent(final GroovyPresentationModel gpm) {
        JTextField jtextfield = new JTextField();
        jtextfield.setName(name);
        jtextfield.setColumns(getColumns());

        ValidationComponentUtils.setMessageKey(jtextfield, getName());

        if (isMandatory()) {
            ValidationComponentUtils.setMandatory(jtextfield, true);
        }
        if (!isEditable()) {
            jtextfield.setEditable(false);
        }

        Bindings.bind(jtextfield, gpm.getModel(getName()));
        return jtextfield;
    }

}
