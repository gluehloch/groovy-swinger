package de.awtools.groovy.swinger.builder

import javax.swing.JTextField;

import com.jgoodies.binding.adapter.Bindings;
import com.jgoodies.validation.view.ValidationComponentUtils;

import de.awtools.groovy.swinger.bean.GroovyPresentationModel;

/**
 * Example:
 * <pre>
 *
 * def model = new GuiModel(name: 'Winkler')
 * GroovyPropertyChangeSupportBuilder.preparePCLMechanics(bean)
 *
 * view {
 *     bean model
 *     textfield {
 *         name 'name'
 *         columns 10
 *         mandatory true
 *         editable true
 *         bind 'name'
 *     }
 * }
 *
 * bind view bean with name  
 *
 * </pre>
 */
class GroovyTextfield {
    
    private String name;
    
    private boolean mandatory = false;
    
    private int columns;
    
    private boolean editable = true;
    
    private Textfield(final String _name, final int _columns,
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
