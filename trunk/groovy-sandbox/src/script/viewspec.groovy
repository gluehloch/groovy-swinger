class ViewSpecification {

    def name = 'ViewSpecification_Part_I'
    def model = new ViewModel()

    def view = {
        bean { model }

        textfield {
            name '_name_'
            columns 10
            mandatory true
            editable true
            bind 'name'
        }

        textfield {
            name '_forename_'
            columns 10
            mandatory true
            editable true
            bind 'forename'
        }

    }
    
}

class  Textfield {

    def name
    def columns
    def mandatory
    def editable
    def binding

    String toString() {
        """
    [Textfield.class
        name: ${name},
        columns: ${columns},
        mandatory: ${mandatory},
        editable: ${editable},
        binding: ${binding}
    ]
        """
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
    def model

    String toString() {
        println "View defintion:"
        println "    model: ${model}"
        println "    elements:"
        elements.each { key, value ->
            println "${value}"
        }
    }

    def name(value) {
        println "    DSLReader#name(${value})"
        currentElement.name = value
        elements[currentElement.name] = currentElement
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
        if (name == 'bean') {
            println "    bind to a model"
            model = args[0]()
            println "    The model: ${model}"
        } else if (name == 'textfield') {
            println "    create a textfield definition"
            currentElement = new Textfield()
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

println "Result: ${dslReader}"

return 0

