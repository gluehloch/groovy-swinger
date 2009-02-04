def clos = {
    println 'this: ' + this.class.name
    println 'delegate: ' + delegate.class.name
    return delegate.class.name
}

assert 'DelegateExample' == clos()
clos.delegate = new Integer(100)
assert 'java.lang.Integer' == clos()

