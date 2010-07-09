class ViewSpecification {

    def name = 'ViewSpecification_Part_I'

    def view = {
        bean { new ViewModel() }
        textfield {
            columns 10
            mandatory true
            editable true
            bind 'name'
        }
        
        'Ok'
    }
    
}

class  Textfield {

    def name
    def columns
    def mandatory
    def editable
    def binding

    String toString() {
        "columns: ${columns}, mandatory: ${mandatory}, editable: ${editable}, binding: ${binding}"
    }

}

class ViewModel {

    def name = 'winkler'
    def forename = 'andre'

    String toString() {
        name + "," + forename
    }
}

class DSLReader {

    def elements = [:]
    def currentElement

    String toString() {
        elements.each { key, value ->
            println "key: ${key}, value: ${value}"
        }
    }

    def columns(value) {
        println "    DSLReader#columns(${value})"
        currentElement.columns = value
    }

    def mandatory(value) {
        println "    DSLReader#mandatory(${value})"
        currentElement.mandatory = value
    }

    def editable(value) {
        println "    DSLReader#editable(${value})"
        currentElement.editable = value
    }
    
    def bind(value) {
        println "    DSLReader#bind(${value})"
        currentElement.binding = value
    }

    def invokeMethod(String name, args) {
        if (name == 'textfield') {
            println "    create a textfield definition"
            currentElement = new Textfield()
            elements['textfield'] = currentElement
        }

        println "    DSLReader#invokeMethod ${name}(...)"
        args.eachWithIndex { arg, idx ->
            println "        Argument ${idx}: ${arg}"
        }
    }

    def propertyMissing(String name) {
        println "    DSLReader#missingProperty ${name}"
        "missingProperty"
    }

    def propertyMissing(String name, value) {
        println "    DSLReader#missingProperty ${name}"
    }

}

def dslReader = new DSLReader()
def viewSpec = new ViewSpecification()

viewSpec.view.delegate = dslReader
viewSpec.view.resolveStrategy = Closure.DELEGATE_FIRST

println "Execute: ${viewSpec.view()}"

println "Result: ${dslReader.toString()}"

//viewSpec.view.resolveStrategy = Closure.DELEGATE_FIRST
//
//println "Execute: ${viewSpec.view()}"


