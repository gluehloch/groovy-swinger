package de.gluehloch.sandbox.groovy.bean

import java.beans.PropertyChangeListener

/**
 * Verbindet eine Bean-Eigenschaft und ein ValueModel miteinander. D.h.
 * Änderungen an der Bean-Eigenschaft übertragen sich auf das ValueModel
 * und umgekehrt.
 */
class PropertyValueModelConnector {

	def valueModel
	def bean
	def propertyName

	private def valueListener
	private def propertyListener

	void connect() {
        propertyListener = { event ->
        	valueModel.value = event.newValue
        } as PropertyChangeListener
        bean.addPropertyChangeListener(propertyName, propertyListener)

        valueListener = { event ->
        	bean.@"$propertyName" = event.newValue
        } as PropertyChangeListener
        valueModel.addValueChangeListener(valueListener)
	}

	void disconnect() {
		valueModel.removeValueChangeListener(valueListener)
		bean.removePropertyChangeListener(propertyListener)
		valueListener = null
		propertyListener = null
	}

}
