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

	/** Die Verbinder zwischen ValueModel und Bean-Eigenschaft. */
	def connectors = []

	/**
	 * Das hier �bergebene Bean muss PCL Eigenschaften besitzen. Entweder
	 * auf nat�rliche Art und Weise oder �ber
	 * GroovyPropertyChangeSupportBuilder.
	 *
	 * @param _bean Das gewrappte Bean.
	 */
	GroovyPresentationModel(_bean) {
		bean = _bean
	}

	/**
	 * Liefert ein JGoodies ValueModel f�r eine Eigenschaft des gewrappten
	 * Beans.
	 *
	 * @param propertyName Der Name der Eigenschaft.
	 * @return Ein JGoodies ValueModel.
	 */
	ValueModel getModel(propertyName) {
		if (!bean.metaClass.properties.find { it.name == propertyName }) {
			throw new IllegalArgumentException("Unknown property ${propertyName}")
		}

		def vh = valueHolders[propertyName]
		if (!vh) {
			vh = new ValueHolder()
			valueHolders[propertyName] = vh
		}

		def pvmc = new PropertyValueModelConnector(
			valueModel: vh, bean: bean, propertyName: propertyName)
		pvmc.connect()
		connectors << pvmc

		return vh
	}

	def unbind() {
		connectors.each { pvmc ->
			pvmc.disconnect()
		}
 	}

}