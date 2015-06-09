

# The delegate keyword #
The `delegate` keyword is only available in closures! Take a look at the following example:
```
class DelegateExample {
    def clos = {
        println 'this: ' + this.class.name
        println 'delegate: ' + delegate.class.name
        println 'owner: ' + owner.class.name
        delegate.class.name
    }
}

def example = new DelegateExample()

assert 'DelegateExample' == example.clos()
example.clos.delegate = new Integer(100)
assert 'java.lang.Integer' == example.clos()
```

This piece of code prints out the following statements:
```
this: DelegateExample
delegate: DelegateExample
owner: DelegateExample
this: DelegateExample
delegate: java.lang.Integer
owner: DelegateExample

```

Per default `delegate` references the same object as `this`. But you have the possibility to change the value `delegate` instead of `this`, which is unmodifiable.

# Resolver Strategy #
The ResolverStrategy defines the lookup mechanism to find methods and properties of a closure. Example:
```
class AnExample {
    def clos = {
        add(1, 2)
    }

    def add(a, b) {
        a + b
    }
}

def e = new AnExample()
assert e.clos() == 3
```
In this case the closure calls the add method of the closure´s owner. The so called `OWNER_FIRST` strategy. Another opportunity would be to set the resolver strategy to `DELEGATE_FIRST`. Then the delegate would be the first object to search for the method `add`. Take a look on the Groovy API for [Closures](http://groovy.codehaus.org/api/groovy/lang/Closure.html) Example:
```
class AnExample {
    def clos = {
        add(1, 2)
    }

    def add(a, b) {
        a + b
    }
}

class AnotherClass {
    def add(a, b) {
        a + b + 1
    }
}

def e = new AnExample()
def f = new AnotherClass()

e.clos.delegate = f
e.clos.resolveStrategy = Closure.DELEGATE_FIRST
assert e.clos() == 4
```
Here the method `add` of the `AnotherClass` will be called.

# Advanced articles #
This [article](http://naleid.com/blog/2009/06/25/groovy-closures-make-unit-testing-with-soft-asserts-simple) gives interesting informations about closures and delegate. The author extends the JUnit test framework with soft assertions. This modified assertion collects all errors and throws at the end of the test procedure an exception.