class Example {
    def func = {
        second()
    }
    
    def second = {
        "second value"
    }

    def invokeMethod(String name, args) {
        println "    Example#invokeMethod ${name}()"
        super.invokeMethod(name, args)
    }

    def propertyMissing(String name) {
        println "    Example#missingProperty ${name}"
        "missingProperty"
    }

    def propertyMissing(String name, value) {
        println "    Example#missingProperty ${name}"
    }
}

class AnotherClass {
    def second = {
        "another value"
    }
}

println "Hello Andre"
def example = new Example()

println "1: ${example.func()}"
example.func.delegate = new AnotherClass()
println "2: ${example.func()}"

println """
# Change the resolveStrategy and call func() again:
"""

example.func.resolveStrategy = Closure.DELEGATE_FIRST
println "3: ${example.func()}"

println "4: Invoke missing method: ${example.stichmon}"

