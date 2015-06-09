# Problem description #

The assumption is the following Groovy bean:
```
class Person {
    def name
    def age
}
```

Next i write some piece of Java Code:
```
Person person = new Person();
person.setAge(40);
person.setName("Winkler");

GroovyPropertyChangeSupportBuilder.preparePCLMechanics(person);
GroovyPresentationModel gpm = new GroovyPresentationModel(person);
```

The following rules are valid (Defined as Java code):
```
ValueModel vm = gpm.getModel("name");
assertEquals(vm, gpm.getModel("name"));
assertEquals("Winkler", vm.getValue());

vm.setValue("Hallo");
// Propagate the change to the bean
assertEquals(vm.getValue(), person.getName());

// !!! Property change support works fine!!!
person.setProperty("name", "Winkler-the-new");
assertEquals("Winkler-the-new", vm.getValue());
assertEquals("Winkler-the-new", gpm.getModel("name").getValue());
		
// !!! Property change support doesn´t work, if you call
person.setName("Was_anderes");
assertEquals("Was_anderes", person.getName());

// --> BUT the property change was not propagated to the value model. <--
assertEquals("Winkler-the-new", vm.getValue()); // The ValueModel has the old value
assertEquals("Winkler-the-new", gpm.getModel("name").getValue());
person.setProperty("name", "Winkler-the-new");
```

If your Java code wants to benefit from GroovyPropertyChangeSupportBuilder, then you have to call the `#setProperty(String,Object)` method. The direct call of `#setName(String)` isn´t catched by the GroovyPropertyChangeSupportBuilder.

I expected another behavior. But this is the reality.

Also take a look on ticket http://code.google.com/p/groovy-swinger/issues/detail?id=1