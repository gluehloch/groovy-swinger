package de.gluehloch.sandbox.groovy.bean

import com.jgoodies.binding.value.*

import java.beans.PropertyChangeListener

/**
 * In Anlehnung an das JGoodies PresentationModel.
 */
class GroovyPresentationModel {

	/** Das Bean dessen Properties bearbeitet werden */
	def bean

	/** Alle angelegten ValueHolders werden hier abgelegt. */
	def valueHolders = [:]

	/**
	 * Das hier übergebene Bean muss PCL Eigenschaften besitzen. Entweder
	 * auf natürliche Art und Weise oder über
	 * GroovyPropertyChangeSupportBuilder.
	 *
	 * @param _bean Das gewrappte Bean.
	 */
	GroovyPresentationModel(_bean) {
		bean = _bean
	}

	/**
	 * Liefert ein JGoodies ValueModel für eine Eigenschaft des gewrappten
	 * Beans.
	 *
	 * @param propertyName Der Name der Eigenschaft.
	 * @return Ein JGoodies ValueModel.
	 */
	def getModel(propertyName) {
		if (!bean.metaClass.properties.find { it.name == propertyName }) {
			throw new IllegalArgumentException("Unknown property ${propertyName}")
		}

		def vh = valueHolders[propertyName]
		if (!vh) {
			vh = new ValueHolder()
			valueHolders[propertyName] = vh
		}

        def beanListener = { event ->
        	vh.value = event.newValue
        } as PropertyChangeListener
        bean.addPropertyChangeListener(propertyName, beanListener)

        def valueListener = { event ->
        	bean.@"$propertyName" = event.newValue
        } as PropertyChangeListener
        vh.addValueChangeListener(valueListener)

		return vh
	}

	def unbind() {
		valueHolders.each { key, value ->
			valueHolder.removeValueChangeListener
		}
 	}

}
